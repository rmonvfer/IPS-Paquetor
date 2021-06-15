package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.routes.Route;
import es.uniovi.eii.paquetor.repositories.RoutesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoutesService {
    @Autowired
    UsersService usersService;

    @Autowired
    RoutesRepository routesRepository;

    @Autowired
    LocationsService locationsService;

    public Route getEmployeeRouteById(UUID employeeUUID) {
        return usersService.getUser(employeeUUID).getRoute();
    }
}
