package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.User;
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

    /**
     * Asigna una ruta a un empleado.
     * @param route ruta a asignar
     * @param employee empleado encargado de recorrer la ruta
     */
    public void assignRouteToEmployee(Route route, User employee) {
        employee.setRoute(route);
        usersRepository.save(employee);
    }

    /**
     * Registra un Empleado.
     * @param user Empleado a registrar
     */
    public void addEmployee(User employee) {
        employee.setRole(rolesService.getRoles()[1])
                .setPassword(bCryptPasswordEncoder.encode(employee.getPasswordConfirm()))
                .setId(UUID.randomUUID());
        usersRepository.save(employee);
        log.info("Added Employee with values: " + employee);
    }
}
