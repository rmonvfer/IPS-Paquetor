package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.locations.City;
import es.uniovi.eii.paquetor.entities.locations.Home;
import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.parcels.ParcelPickupOrderType;
import es.uniovi.eii.paquetor.entities.parcels.ParcelStatus;
import es.uniovi.eii.paquetor.repositories.RoutesRepository;
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
    private RoutesRepository routesRepository;

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
        Location ramonHome = new Home()
                .setCity(Oviedo)
                .setCalle("Avenida del Olmo")
                .setCodigoPostal(33000)
                .setNumero(13)
                .setPiso("5")
                .setPuerta("Izq");
        locationsService.addLocation(ramonHome); // No es de verdad `O´

        Location u1Home = new Home()
                .setCity(Oviedo)
                .setCalle("Valdés Salas")
                .setCodigoPostal(33007)
                .setNumero(0);
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
        warehousesService.initWarehouseInternalRoutes((Warehouse) warehouseOviedo);
        warehousesService.initWarehouseTransferzone((Warehouse) warehouseOviedo);
        locationsService.addLocation(warehouseOviedo);

        Location warehouseMadrid = new Warehouse()
                .setCity(Madrid)
                .setCodigoPostal(44005)
                .setCalle("Polígono muy lejano")
                .setNumero(9991);
        warehousesService.initWarehouseInternalRoutes((Warehouse) warehouseMadrid);
        warehousesService.initWarehouseTransferzone((Warehouse) warehouseMadrid);
        locationsService.addLocation(warehouseMadrid);

        /* Insertar usuarios */
        log.info("Inserting customers...");
        User ramon = new User("ramonvilafer@gmail.com");
        ramon.setName("ramon")
                .setLastName("vila")
                .setDNI("71881410H") // Tampoco es de verdad
                .setPasswordConfirm(password)
                .setLocation(ramonHome);
        usersService.addCustomer(ramon);

        User user1 = new User("cu1@email.com");
        user1.setName("Customer 1")
                .setLastName("First")
                .setDNI("12345678B")
                .setPasswordConfirm(password)
                .setLocation(u1Home);
        usersService.addCustomer(user1);

        User user2 = new User("cu2@email.com");
        user2.setName("Customer 2")
                .setLastName("Second")
                .setDNI("12345678C")
                .setPasswordConfirm(password)
                .setLocation(u2Home);
        usersService.addCustomer(user2);

        log.info("Inserting parcels...");

        /* Insertar paquetes */
        // User1 -> User2 con recogida a domicilio (REMOTE)
        UUID u1_to_u2_uuid = parcelsService.registerNewParcel(
                user1, user2, 12.0, 120.0, 190.0, 140.0);
        parcelsService.processParcelPickupOrder(
                parcelsService.getParcel(u1_to_u2_uuid), ParcelPickupOrderType.REMOTE);

        log.info("Emulating state change for Parcel " + parcelsService.getParcel(u1_to_u2_uuid));
        parcelsService.updateParcelStatus(parcelsService.getParcel(u1_to_u2_uuid), ParcelStatus.IN_PICKUP);
        wait(5);

        parcelsService.updateParcelStatus(parcelsService.getParcel(u1_to_u2_uuid), ParcelStatus.IN_ORIGIN);
        wait(5);

        parcelsService.updateParcelStatus(parcelsService.getParcel(u1_to_u2_uuid), ParcelStatus.IN_DESTINY);
        wait(5);

        parcelsService.updateParcelStatus(parcelsService.getParcel(u1_to_u2_uuid), ParcelStatus.IN_DELIVERY);
        wait(5);

        parcelsService.updateParcelStatus(parcelsService.getParcel(u1_to_u2_uuid), ParcelStatus.DELIVERED);
        wait(6);

        log.info("Status change result: " + parcelsService.getParcel(u1_to_u2_uuid));

        // User2 -> User1 con recogida a domicilio (REMOTE)
        UUID u2_to_u1_uuid = parcelsService.registerNewParcel(
                user2, user1, 12.0, 120.0, 190.0, 140.0);
        parcelsService.processParcelPickupOrder(
                parcelsService.getParcel(u2_to_u1_uuid), ParcelPickupOrderType.REMOTE);

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