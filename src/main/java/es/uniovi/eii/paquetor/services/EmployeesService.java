package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.locations.City;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.routes.Route;
import es.uniovi.eii.paquetor.repositories.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @Log4j2
public class EmployeesService {

    @Autowired
    RolesService rolesService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UsersService usersService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoutesService routesService;

    @Autowired
    WarehousesService warehousesService;

    /**
     * Asigna un empleado a la ruta interna de su almacén de referencia
     * @param employee empleado encargado de recorrer la ruta
     */
    public void assignInternalRouteToEmployee(User employee) {
        employee.setRoute(getEmployeeWarehouse(employee).getInternalRoute());
        usersRepository.save(employee);
    }

    /**
     * Asigna a un empleado una ruta externa hacia la ciudad especificada.
     * En caso de no existir crea una ruta vacía.
     * @param targetCity Ciudad de destino de la ruta externa.
     * @param employee Empleado encargado de la ruta.
     */
    public void assignExternalRouteToEmployee(City targetCity, User employee) {
        log.info("Assigning external route to employee " + employee);

        Warehouse employeeWarehouse = getEmployeeWarehouse(employee);

        log.info("Found employee warehouse " + employeeWarehouse);

        Route externalRoute = warehousesService.getExternalRouteToCity(employeeWarehouse, targetCity);
        if (externalRoute == null) {
            externalRoute = routesService.createExternalRoute(targetCity);
        }
        employee.setRoute(externalRoute);
    }

    /**
     * Obtiene el almacén con el que está asociado un empleado
     * @param employee Empleado
     * @return Almacén de referencia del empleado
     */
    public Warehouse getEmployeeWarehouse(User employee) {
        return (Warehouse) employee.getLocation();
    }

    /**
     * Registra un Empleado.
     * @param employee Empleado a registrar
     */
    public void addEmployee(User employee) {
        employee.setRole(rolesService.getRoles()[1])
                .setPassword(bCryptPasswordEncoder.encode(employee.getPasswordConfirm()))
                .setId(UUID.randomUUID());
        usersRepository.save(employee);
        log.info("Added Employee with values: " + employee);
    }
}
