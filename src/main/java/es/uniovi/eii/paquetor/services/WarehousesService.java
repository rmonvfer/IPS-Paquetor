package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.locations.transferZone.WarehouseTransferZone;
import es.uniovi.eii.paquetor.entities.routes.Route;
import es.uniovi.eii.paquetor.entities.routes.RouteStop;
import es.uniovi.eii.paquetor.entities.routes.RouteStopType;
import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.locations.City;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.locations.transferZone.WarehouseTransferzoneSection;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.parcels.ParcelStatus;
import es.uniovi.eii.paquetor.entities.routes.RouteType;
import es.uniovi.eii.paquetor.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service @Log4j2
public class WarehousesService {

    @Autowired
    LocationsRepository locationsRepository;

    @Autowired
    ParcelsService parcelsService;

    @Autowired
    RoutesService routesService;

    @Autowired
    RoutesRepository routesRepository;

    @Autowired
    CitiesRepository citiesRepository;

    @Autowired
    RouteStopRepository routeStopRepository;

    @Autowired
    WarehouseTransferZoneRepository warehouseTransferZoneRepository;

    @Autowired
    WarehouseTransferzoneSectionRepository warehouseTransferzoneSectionRepository;

    /**
     * Añade un paquete a la ruta interna del almacén ubicado en la misma
     * ciudad del emisor del paquete.
     * @param parcel paquete a añadir.
     */
    public void addParcelToInternalRoute(Parcel parcel, RouteStopType stopType) {
        log.info("Adding Parcel to Warehouse internal route");

        User sender = parcel.getSender();
        Warehouse senderReferenceWarehouse = findByCityName(sender.getLocation().getCity().getName());
        log.info("Reference Warehouse found in " + sender.getLocation().getCity().getName());

        // Crear una parada de ruta e introducir el paquete y el tipo de parada
        RouteStop customerRouteStop = new RouteStop().setType(stopType).addParcel(parcel);
        routeStopRepository.save(customerRouteStop);

        senderReferenceWarehouse.getInternalRoute().getRouteStops().add(customerRouteStop);
        log.info("A RouteStop has been added to the previously found reference warehouse");

        routesRepository.save(senderReferenceWarehouse.getInternalRoute());

        locationsRepository.save(senderReferenceWarehouse);
        log.info("Parcel added to internal route successfully!");
    }

    /**
     * Genera las rutas externas necesarias para entregar todos los paquetes
     * de la zona de transferencia.
     * @param warehouse
     */
    public void processTransferzone(Warehouse warehouse) {
        log.info("Processing transferzone from warehouse " + warehouse);
        WarehouseTransferZone transferZone = warehouse.getTransferZone();

        log.info("Got transferzone " + transferZone);
        Set<WarehouseTransferzoneSection> transferzoneSections = transferZone.getWarehouseTransferzoneSections();

        log.info("Got transferzone sections " + transferzoneSections);

        // Recorrer los paquetes de la zona de transferencia y asignarlos a la ruta externa correspondiente
        for (WarehouseTransferzoneSection section : transferzoneSections) {
            for (Parcel parcel : section.getParcels()) {
                addParcelToExternalRoute(parcel);
            }
        }
        locationsRepository.save(warehouse);
    }

    /**
     * Añade un paquete a la ruta externa correspondiente a la ciudad de residencia del destinatario.
     * @param parcel Paquete a añadir.
     */
    public void addParcelToExternalRoute(Parcel parcel) {
        log.info("Adding parcel to external route");
        Warehouse origin = findParcelSenderReferenceWarehouse(parcel);
        Warehouse target = findParcelRecipientReferenceWarehouse(parcel);
        Route externalRoute = getExternalRouteToCity(origin, target.getCity());

        // Comprobar que existe la ruta externa, si no, crearla
        if (externalRoute == null) {
            log.warn("External route is null, adding a new external route");
            externalRoute = addExternalRouteToWarehouse(origin, target);
            log.info("Resulting external route = " + externalRoute);
        }
        parcelsService.updateParcelStatus(parcel, ParcelStatus.IN_LONG_DISTANCE_TRANSPORT);

        RouteStop routeStop = externalRoute.getRouteStops().iterator().next();
        routeStop.addParcel(parcel);
        routeStopRepository.save(routeStop);
        routesRepository.save(externalRoute);
    }

    /**
     * Crea una ruta a un almacén externo para repartir los paquetes que lo tengan como destino.
     * @param originWarehouse Almacén de origen
     * @param targetWarehouse Almacén de destino
     */
    public Route addExternalRouteToWarehouse(Warehouse originWarehouse, Warehouse targetWarehouse) {
        log.info("Attempting to add a new external route from " + originWarehouse + " to " + targetWarehouse);

        Route newRoute = routesService.createExternalRoute(targetWarehouse.getCity());

        originWarehouse.addExternalRoute(newRoute);
        log.info("External route added to warehouse");

        // Persistir los cambios
        locationsRepository.save(originWarehouse);
        log.info("Done adding the external route");

        return newRoute;
    }

    /**
     * Añade un paquete a la zona de transferencia correspondiente del almacén.
     * @param parcel Paquete que se traslada.
     */
    public void addParcelToTransferzone(Parcel parcel) {
        log.info("Adding parcel to transferzone from " + parcel);

        // Obtener el almacén de salida del paquete (el ubicado en la ciudad del emisor)
        Warehouse warehouse = findParcelSenderReferenceWarehouse(parcel);

        // Obtener la ciudad de destino del paquete
        City targetCity = parcel.getRecipient().getLocation().getCity();

        // Buscar una sección en la zona de transferencia que tenga el mismo nombre que la ciudad de destino
        WarehouseTransferzoneSection transferzoneSection =
                warehouseTransferzoneSectionRepository.findByCity_NameIgnoreCase(targetCity.getName());

        log.info("Found WarehouseTransferzone " + transferzoneSection);

        if (transferzoneSection != null) {
            // Añadir el paquete a la lista de paquetes de la sección de la zona de transferencia
            transferzoneSection.getParcels().add(parcel);

        } else {
            // Si no hay una sección en la zona de transferencia para esa ciudad de destino entonces crear una
            // y almacenar el paquete en ella.
            addTransferzoneSection(warehouse, targetCity, parcel);
        }
        parcelsService.updateParcelStatus(parcel, ParcelStatus.IN_TRANSFER_ZONE);
        locationsRepository.save(warehouse);
    }

    /**
     * Obtiene la ruta externa a una ciudad desde un almacén determinado
     * @param warehouse Almacén de origen
     * @param city Ciudad de destino
     * @return Almacén si existe, null en caso contrario
     */
    public Route getExternalRouteToCity(Warehouse warehouse, City city) {

        log.info("Looking for an external route from " + warehouse + " to " + city);

        List<Route> allExternalRoutes =
                routesRepository.findByRouteStops_Location_CityEqualsAndRouteTypeEquals(city, RouteType.EXTERNAL);

        log.info("Found " + allExternalRoutes.size() + " external routes");

        if (allExternalRoutes.size() > 0) {
            log.info("Iterating all external routes");
            for (Route route: allExternalRoutes) {
                for (Route warehouseRoute: warehouse.getExternalRoutes()) {
                    if (warehouseRoute.equals(route)) return route;
                }
            }
        }
        return null;
    }

    /**
     * Determina si existe una sección en la zona de transferencia de una almacén determinado
     * cuyo contenido esté dirigido a una ciudad concreta
     * @param warehouse Almacén que contiene la zona de transferencia.
     * @param city Ciudad de destino de los paquetes de la zona de transferencia.
     * @return true si existe, false en caso contrario.
     */
    public boolean existsTransferzoneSection(Warehouse warehouse, City city) {
        WarehouseTransferzoneSection transferzoneSection =
                warehouseTransferzoneSectionRepository.findByCity_NameIgnoreCase(city.getName());
        return transferzoneSection != null;
    }

    /**
     * Añade una ruta externa a un almacén.
     * @param route
     * @param warehouse
     */
    public void addExternalRouteToWarehouse(Route route, Warehouse warehouse) {
        warehouse.addExternalRoute(route);
        locationsRepository.save(warehouse);
    }

    /**
     * Añade una sección en la zona de transferencia. En cada zona de transferencia habrá una
     * serie de paquetes asociados a una ciudad individual.
     * @param warehouse Almacén a modificar
     * @param city Ciudad (clave)
     * @param parcels Paquetes a enviar la ciudad destino (valores)
     */
    public void addTransferzoneSection(Warehouse warehouse, City city, List<Parcel> parcels) {
        warehouse.getTransferZone()
                .getWarehouseTransferzoneSections().add(new WarehouseTransferzoneSection(city, parcels));
    }

    public void addTransferzoneSection(Warehouse warehouse, City city, Parcel... parcels) {
        log.info("Adding transferzone section to " + warehouse);
        WarehouseTransferZone transferZone = warehouse.getTransferZone();
        WarehouseTransferzoneSection transferzoneSection =
                new WarehouseTransferzoneSection(citiesRepository.findByNameIgnoreCase(city.getName()), parcels);
        warehouseTransferzoneSectionRepository.save(transferzoneSection);

        transferZone.getWarehouseTransferzoneSections().add(transferzoneSection);

        log.info("Result " + warehouse);
        locationsRepository.save(warehouse);
    }

    /**
     * Inicializa un almacén creando una ruta interna y una zona de transferencia
     * @param warehouse
     */
    public void initWarehouse(Warehouse warehouse) {
        Route warehouseInternalRoute = new Route();
        routesRepository.save(warehouseInternalRoute);
        warehouse.setInternalRoute(warehouseInternalRoute);

        WarehouseTransferZone warehouseTransferZone = new WarehouseTransferZone();
        warehouseTransferZoneRepository.save(warehouseTransferZone);
        warehouse.setTransferZone(warehouseTransferZone);

        locationsRepository.save(warehouse);
    }

    /**
     * Restablece la ruta interna de un almacén.
     * Obligatorio tras haber terminado una ruta.
     * @param warehouse
     */
    public void resetWarehouseInternalRoute(Warehouse warehouse) {
        routeStopRepository.deleteAll(warehouse.getInternalRoute().getRouteStops());
        locationsRepository.save(warehouse);
    }

    /**
     * Asocia un empleado con un almacén concreto
     * @param warehouse Almacén que recibe al empleado
     * @param employee Empleado a agregar
     */
    public void addEmployeeToWarehouse(Warehouse warehouse, User employee) {
        warehouse.getEmployees().add(employee);
        locationsRepository.save(warehouse);
    }

    /**
     * Elimina un paquete de una ruta interna.
     * @param parcel
     */
    public void removeParcelFromInternalRoute(Parcel parcel) {
        Warehouse senderWarehouse = findParcelSenderReferenceWarehouse(parcel);
        if (senderWarehouse.getInternalRoute().getRouteStops().contains(parcel)) {
            senderWarehouse.getInternalRoute().getRouteStops().remove(parcel);
        }
        routesRepository.save(senderWarehouse.getInternalRoute());
        locationsRepository.save(senderWarehouse);
    }

    /**
     * Procesa un paquete a su llegada al almacén
     * @param parcel Paquete a procesar
     */
    public void processParcelReception(Parcel parcel) {
        log.info("Processing parcel reception: " + parcel);
        log.info("Is for internal dispatch? " + parcel.isForInternalDispatch());

        parcelsService.updateParcelStatus(parcel, ParcelStatus.IN_ORIGIN);
        removeParcelFromInternalRoute(parcel); // Eliminarlo de la ruta interna

        if (parcel.isForInternalDispatch()) {
            addParcelToInternalRoute(parcel, RouteStopType.DELIVERY);
        } else {
            addParcelToTransferzone(parcel);
        }
    }

    public Warehouse findByCityName(String cityName) {
        return locationsRepository.findByCity_NameEqualsIgnoreCase(cityName);
    }

    public Warehouse findParcelSenderReferenceWarehouse(Parcel parcel) {
        return findByCityName(parcel.getSender().getLocation().getCity().getName());
    }

    public Warehouse findParcelRecipientReferenceWarehouse(Parcel parcel) {
        return findByCityName(parcel.getRecipient().getLocation().getCity().getName());
    }
}
