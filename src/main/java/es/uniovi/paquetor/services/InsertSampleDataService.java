package es.uniovi.paquetor.services;

import es.uniovi.paquetor.entities.Ubicacion;
import es.uniovi.paquetor.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InsertSampleDataService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @PostConstruct
    public void init() {

        Ubicacion u1 = new Ubicacion("Oviedo", "Calle T", 1);
        Ubicacion u2 = new Ubicacion("Madrid", "Calle R", 5);
        Ubicacion u3 = new Ubicacion("Barcelona", "Calle M", 14);
        Ubicacion u4 = new Ubicacion("Oviedo", "Calle C", 4);
        Ubicacion u5 = new Ubicacion("Zaragoza", "Calle P", 16);
        Ubicacion u6 = new Ubicacion("Córdoba", "Calle O", 45);
        Ubicacion u7 = new Ubicacion("Barcelona", "Calle N", 91);
        Ubicacion u8 = new Ubicacion("Gijón", "Calle F", 23);
        Ubicacion u9 = new Ubicacion("Oviedo", "Calle X", 27);

        Usuario user1 = new Usuario( "user1@email.com", "12345", "Usuario 1", u1 );
        user1.setRole(rolesService.getRoles().get("USER"));

        Usuario user2 = new Usuario( "user2@email.com", "12345", "Usuario 2", u2 );
        user2.setRole(rolesService.getRoles().get("USER"));

        Usuario user3 = new Usuario( "user3@email.com", "12345", "Usuario 3", u3 );
        user3.setRole(rolesService.getRoles().get("USER"));

        Usuario user4 = new Usuario( "user4@email.com", "12345", "Usuario 4", u4 );
        user4.setRole(rolesService.getRoles().get("USER"));

        Usuario user5 = new Usuario( "user5@email.com", "12345", "Usuario 5", u5 );
        user5.setRole(rolesService.getRoles().get("USER"));

        Usuario user6 = new Usuario( "user6@email.com", "12345", "Usuario 6", u6 );
        user6.setRole(rolesService.getRoles().get("USER"));

        Usuario user7 = new Usuario( "user7@email.com", "12345", "Usuario 7", u7 );
        user7.setRole(rolesService.getRoles().get("USER"));

        Usuario user8 = new Usuario( "user8@email.com", "12345", "Usuario 8", u8 );
        user8.setRole(rolesService.getRoles().get("USER"));

        Usuario user9 = new Usuario( "user9@email.com", "12345", "Usuario 9", u9 );
        user9.setRole(rolesService.getRoles().get("USER"));

        // TODO: Crear e insertar empleados, almacenes, transportes, rutas, paquetes...

        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
        usersService.addUser(user6);
        usersService.addUser(user7);
        usersService.addUser(user8);
        usersService.addUser(user9);

        System.out.println( "Usuario 7: " );
        System.out.println( usersService.getUserByEmail("user7@email.com") );
    }
}