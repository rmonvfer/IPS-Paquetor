package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.locations.City;
import es.uniovi.eii.paquetor.entities.locations.Home;
import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.parcels.ParcelPickupOrderType;
import es.uniovi.eii.paquetor.entities.parcels.ParcelStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;


@Service
@Log4j2
public class InsertSampleDataService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ParcelsService parcelsService;

    @Autowired
    private CitiesService citiesService;

    @Autowired
    private LocationsService locationsService;

    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private WarehousesService warehousesService;

    @PostConstruct
    public void init() {
        String password = "password";

        /* Insertar ciudades */
        City Oviedo = new City("Oviedo");
        City Madrid = new City("Madrid");
        City Barcelona = new City("Barcelona");
        City Sevilla = new City("Sevilla");
        City Bilbao = new City("Bilbao");
        City Leon = new City("León");
        City Salamanca = new City("Salamanca");
        City Cadiz = new City("Cadiz");
        citiesService.saveAll(Oviedo, Madrid, Barcelona, Sevilla, Bilbao, Leon, Salamanca, Cadiz);

        /* Insertar domicilios */
        log.info("Inserting homes...");

        Location u1Home = new Home()
                .setCity(Oviedo)
                .setCalle("Valdés Salas")
                .setCodigoPostal(33007)
                .setNumero(7);
        locationsService.addLocation(u1Home);

        Location u2Home = new Home()
                .setCity(Oviedo)
                .setCalle("Valdés Salas")
                .setCodigoPostal(33007)
                .setNumero(5);
        locationsService.addLocation(u2Home);

        Location u3Home = new Home()
                .setCity(Madrid)
                .setCalle("Principal")
                .setCodigoPostal(44012)
                .setNumero(5);
        locationsService.addLocation(u3Home);

        /* Insertar almacenes */
        log.info("Inserting warehouses...");
        Location warehouseOviedo = new Warehouse()
                .setCity(Oviedo)
                .setCodigoPostal(33005)
                .setCalle("Polígono de Asipo")
                .setNumero(125);
        warehousesService.initWarehouse((Warehouse) warehouseOviedo);

        Location warehouseMadrid = new Warehouse()
                .setCity(Madrid)
                .setCodigoPostal(44005)
                .setCalle("Polígono muy lejano")
                .setNumero(9991);
        warehousesService.initWarehouse((Warehouse) warehouseMadrid);

        /* Insertar usuarios */
        log.info("Inserting customers...");

        User user1 = new User("cu1@email.com");
        user1.setName("Jose")
                .setLastName("Fernández")
                .setDNI("12345678B")
                .setPasswordConfirm(password)
                .setLocation(u1Home);
        usersService.addCustomer(user1);

        User user2 = new User("cu2@email.com");
        user2.setName("María")
                .setLastName("González Suarez")
                .setDNI("12345678C")
                .setPasswordConfirm(password)
                .setLocation(u2Home);
        usersService.addCustomer(user2);

        User user3 = new User("cu3@email.com");
        user3.setName("Francisco")
                .setLastName("Galvez")
                .setDNI("12345670C")
                .setPasswordConfirm(password)
                .setLocation(u3Home);
        usersService.addCustomer(user3);

        /* Insertar empleados */
        log.info("Inserting employees...");
        User emp1 = new User("emp1@email.com");
        emp1.setName("Andrea")
                .setLastName("González")
                .setDNI("18754981H")
                .setPasswordConfirm(password)
                .setLocation(warehouseOviedo);
        employeesService.addEmployee(emp1);

        User emp2 = new User("emp2@email.com");
        emp2.setName("Employee 2")
                .setLastName("Second")
                .setDNI("18754981H")
                .setPasswordConfirm(password)
                .setLocation(warehouseOviedo);
        employeesService.addEmployee(emp2);

        log.info("Inserting parcels...");

        // User1 -> User2 INTERNO
        Parcel parcel_1  = parcelsService.registerNewParcel(user1, user2, 12.0, 120.0, 190.0, 140.0);
        Parcel parcel_2  = parcelsService.registerNewParcel(user2, user1, 12.0, 120.0, 190.0, 140.0);
        Parcel parcel_3  = parcelsService.registerNewParcel(user1, user2, 19.0, 120.0, 190.0, 140.0);

        // Marcar para recogida
        parcelsService.processParcelPickupOrder(parcel_1, ParcelPickupOrderType.REMOTE);
        parcelsService.processParcelPickupOrder(parcel_2, ParcelPickupOrderType.REMOTE);
        parcelsService.processParcelPickupOrder(parcel_3, ParcelPickupOrderType.REMOTE);


        // Al llegar al almacén comprueba el destino del paquete
        // warehousesService.processParcelReception(parcel_1);

        /*
        // User 2 -> User 3 (Externo) con recogida a domicilio
        Parcel parcel_2 = parcelsService.registerNewParcel(user2, user3, 12.0, 120.0, 190.0, 140.0);

        parcelsService.processParcelPickupOrder(parcel_2, ParcelPickupOrderType.REMOTE);

        // Tras la recogida, el transportista carga el paquete en la furgoneta
        parcelsService.updateParcelStatus(parcel_2, ParcelStatus.PICKED_UP);

        // Al llegar al almacén comprueba el destino del paquete
        warehousesService.processParcelReception(parcel_2);

        warehousesService.processTransferzone(
                warehousesService.findParcelSenderReferenceWarehouse(parcel_2));
         */

        log.info("Linking employees with routes...");
        /* Asociar empleados con las diferentes rutas */

        // Almacén Oviedo -> Ruta interna (emp1)
        log.info("[INTERNAL] Oviedo -> Oviedo (Employee1)");
        employeesService.assignInternalRouteToEmployee(emp1);
        log.info("Resulting employee " + emp1);

        /*
        // Oviedo -> Madrid
        log.info("[EXTERNAL] Oviedo -> Madrid (Employee2)");
        employeesService.assignEmployeeExternalRouteToCity(Madrid, emp2);
        log.info("Resulting employee " + emp2);
        */

        log.info("Sample data successfully inserted");
    }

    private void wait(int secondsToSleep) {
        try {
            Thread.sleep(secondsToSleep * 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}