package es.uniovi.eii.paquetor.controllers;

import es.uniovi.eii.paquetor.dtos.ParcelStatusUpdateDTO;
import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.parcels.ParcelStatus;
import es.uniovi.eii.paquetor.entities.routes.Route;
import es.uniovi.eii.paquetor.entities.routes.RouteStop;
import es.uniovi.eii.paquetor.entities.routes.RouteStopType;
import es.uniovi.eii.paquetor.repositories.RouteStopRepository;
import es.uniovi.eii.paquetor.repositories.RoutesRepository;
import es.uniovi.eii.paquetor.services.EmployeesService;
import es.uniovi.eii.paquetor.services.ParcelsService;
import es.uniovi.eii.paquetor.services.UsersService;
import es.uniovi.eii.paquetor.services.WarehousesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller @Log4j2
public class EmployeesController {

    @Autowired
    ParcelsService parcelsService;

    @Autowired
    UsersService usersService;

    @Autowired
    WarehousesService warehousesService;

    // Sí, ya se que esto no es MVC
    @Autowired
    RoutesRepository routesRepository;

    // Esto tampoco
    @Autowired
    RouteStopRepository routeStopRepository;

    @Autowired
    EmployeesService employeesService;

    @GetMapping({"/employee/home"})
    public String employeeHome(Model model) {
        User loggedInEmployee = usersService.getLoggedInUser();
        model.addAttribute("routeStops", loggedInEmployee.getRoute().getRouteStops().stream().toList());
        model.addAttribute("route", loggedInEmployee.getRoute());
        model.addAttribute("employee", loggedInEmployee);
        return "employees/home";
    }

    @GetMapping({"/employee/route/{route_id}/{route_stop_id}"})
    public String showRouteStepDetails(@PathVariable Long route_id, @PathVariable Long route_stop_id, Model model) {
        RouteStop currentStop = routeStopRepository.findById(route_stop_id).get();
        model.addAttribute("route", routesRepository.findById(route_id).get());
        model.addAttribute("routeStop", currentStop);
        model.addAttribute("recipient", currentStop.getParcels().stream().iterator().next().getRecipient());
        model.addAttribute("parcels", currentStop.getParcels().stream().toList());
        return "employees/routeStop/details";
    }


    @GetMapping({"/employee/route/process/reception/{route_id}"})
    public String processParcelReception(@PathVariable Long route_id, Model model) {
        Route currentRoute = routesRepository.findById(route_id).get();

        // Estadísticas
        int internalDispatch = 0;
        int externalDispatch = 0;

        // Extraer los paquetes procesados en la ruta
        List<Parcel> pickedParcels = new ArrayList<>();
        for (RouteStop routeStop : currentRoute.getRouteStops()) {
            for (Parcel stopParcel : routeStop.getParcels()) {

                // Contabilizar los paquetes para mostrárselo al transportista
                if (stopParcel.isForInternalDispatch()) internalDispatch++;
                else externalDispatch++;

                pickedParcels.add(stopParcel);
            }
        }

        // Restablecer la ruta interna del almacén.
        Warehouse targetWarehouse = employeesService.getEmployeeWarehouse(usersService.getLoggedInUser());
        warehousesService.resetWarehouseInternalRoute(targetWarehouse);

        // Procesar cada paquete.
        for (Parcel parcel : pickedParcels) {
            warehousesService.processParcelReception(parcel);
        }

        model.addAttribute("internalDispatch", internalDispatch);
        model.addAttribute("externalDispatch", externalDispatch);
        return "employees/postReception";
    }

    @GetMapping({"/employee/route/{route_id}/end"})
    public String finishRoute(@PathVariable Long route_id, Model model) {
        Route currentRoute = routesRepository.findById(route_id).get();
        List<Parcel> pickedParcels = new ArrayList<>();

        for (RouteStop routeStop : currentRoute.getRouteStops()) {
            for (Parcel stopParcel : routeStop.getParcels()) {
                pickedParcels.add(stopParcel);
            }
        }

        log.info("PickedUpParcels: " + pickedParcels);

        model.addAttribute("route", currentRoute);
        model.addAttribute("pickedUpParcels", pickedParcels);
        return "employees/routeEnd";
    }

    @GetMapping({"/employee/route/process/{route_id}/{route_stop_id}"})
    public String processNextRoute(@PathVariable Long route_id, @PathVariable Long route_stop_id) {
        Route currentRoute = routesRepository.findById(route_id).get();
        RouteStop currentStop = routeStopRepository.findById(route_stop_id).get();
        List<Parcel> stopParcels = currentStop.getParcels().stream().toList();

        ParcelStatus newStatus =
                (currentStop.getType() == RouteStopType.PICKUP) ? ParcelStatus.PICKED_UP :  ParcelStatus.DELIVERED;

        // Para cada paquete de la parada
        for (Parcel stopParcel : stopParcels) {
            parcelsService.updateParcelStatus(stopParcel, newStatus); // Marcarlo como recogido / entregado

            log.info("Parcel status updated to " + newStatus);

            // Si el paquete ha sido entregado, eliminarlo de la ruta
            if (newStatus == ParcelStatus.DELIVERED) {
                warehousesService.removeParcelFromInternalRoute(stopParcel);
            }
        }

        // Marcar la parada actual como visitada
        currentStop.setVisited(true);
        routeStopRepository.save(currentStop);
        routesRepository.save(currentRoute);

        // Refrescar la ruta actual
        currentRoute = routesRepository.findById(route_id).get();

        // Extraer las paradas no visitadas
        List<RouteStop> notVisitedRouteStops = currentRoute.getRouteStops()
                .stream().filter(rs -> !rs.isVisited()).collect(Collectors.toList());

        if (notVisitedRouteStops.size() > 0) {
            // Si quedan paradas, extraer la siguiente y redirigir a la vista de detalle
            RouteStop nextStop = currentRoute.getRouteStops().iterator().next();
            return "redirect:/employee/route/" + route_id + "/" + nextStop.getId();

        } else {
            // Si no hay más paradas, mostrar la vista de fin de ruta
            return "redirect:/employee/route/" + route_id + "/end";
        }
    }
}