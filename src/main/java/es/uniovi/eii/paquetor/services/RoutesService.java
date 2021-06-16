package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.locations.City;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.routes.Route;
import es.uniovi.eii.paquetor.entities.routes.RouteStop;
import es.uniovi.eii.paquetor.entities.routes.RouteStopType;
import es.uniovi.eii.paquetor.entities.routes.RouteType;
import es.uniovi.eii.paquetor.repositories.LocationsRepository;
import es.uniovi.eii.paquetor.repositories.RoutesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoutesService {
    @Autowired
    UsersService usersService;

    @Autowired
    RoutesRepository routesRepository;

    @Autowired
    LocationsService locationsService;

    @Autowired
    LocationsRepository locationsRepository;

    /**
     * Crea una ruta externa con una parada en el almacén de destino.
     * @param targetCity Ciudad de destino de la ruta
     * @return ruta creada
     */
    public Route createExternalRoute(City targetCity) {
        RouteStop targetWarehouseRouteStop =
                new RouteStop().setType(RouteStopType.WAREHOUSE_DELIVERY)
                        .setLocation(locationsRepository.findByCity_NameEqualsIgnoreCase(targetCity.getName()));
        Route externalRoute = new Route().setRouteType(RouteType.EXTERNAL).addRouteStop(targetWarehouseRouteStop);
        routesRepository.save(externalRoute);
        return externalRoute;
    }

    public Route getEmployeeRouteById(UUID employeeUUID) {
        return usersService.getUser(employeeUUID).getRoute();
    }
}
