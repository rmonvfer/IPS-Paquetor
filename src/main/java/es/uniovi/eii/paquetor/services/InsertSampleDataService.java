package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.Route;
import es.uniovi.eii.paquetor.entities.User;
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
    private LocationsService locationsService;

    @Autowired
    private RoutesRepository routesRepository;

    @PostConstruct
    public void init() {
        String password = "password";

        /* Insertar domicilios */
        log.info("Inserting homes...");
        Location ramonHome = new Home()
                .setCiudad("Oviedo")
                .setCalle("Avenida del Olmo")
                .setCodigoPostal(33000)
                .setNumero(13)
                .setPiso("5")
                .setPuerta("Izq");

        locationsService.addLocation(ramonHome); // No es de verdad `O´

        Location u1Home = new Home()
                .setCiudad("Oviedo")
                .setCalle("Valdés Salas")
                .setCodigoPostal(33007)
                .setNumero(0);
        locationsService.addLocation(u1Home);

        Location u2Home = new Home()
                .setCiudad("Oviedo")
                .setCalle("Valdés Salas")
                .setCodigoPostal(33007)
                .setNumero(5);
        locationsService.addLocation(u2Home);

        /* Insertar almacenes */
        log.info("Inserting warehouses...");
        Route oviedoInternalRoute = new Route();
        routesRepository.save(oviedoInternalRoute);

        Location warehouseOviedo = new Warehouse()
                .setCiudad("Oviedo")
                .setCodigoPostal(33005)
                .setCalle("Polígono de Asipo")
                .setNumero(125);

        ((Warehouse) warehouseOviedo).setInternalRoute(oviedoInternalRoute);
        locationsService.addLocation(warehouseOviedo);

        /* Insertar usuarios */
        log.info("Inserting customers...");
        User ramon = new User("ramonvilafer@gmail.com");
        ramon.setId(UUID.randomUUID())
                .setName("ramon")
                .setLastName("vila")
                .setDNI("71881410H") // Tampoco es de verdad
                .setPasswordConfirm(password)
                .setLocation(ramonHome);
        usersService.addCustomer(ramon);

        User user1 = new User("cu1@email.com");
        user1.setId(UUID.randomUUID())
                .setName("Customer 1")
                .setLastName("First")
                .setDNI("12345678B")
                .setPasswordConfirm(password)
                .setLocation(u1Home);
        usersService.addCustomer(user1);

        User user2 = new User("cu2@email.com");
        user2.setId(UUID.randomUUID())
                .setName("Customer 2")
                .setLastName("Second")
                .setDNI("12345678C")
                .setPasswordConfirm(password)
                .setLocation(u2Home);
        usersService.addCustomer(user2);

        log.info("Inserting parcels...");
        /* Insertar paquetes */
        // User1 -> User2 con recogida a domicilio (REMOTE)
        UUID u1_to_u2_uuid = parcelsService.registerNewParcel(user1, user2, 120.0, 190.0, 140.0);
        parcelsService.processParcelPickupOrder(parcelsService.getParcel(u1_to_u2_uuid), ParcelPickupOrderType.REMOTE);

        // Simular un cambio de estado (este debería ser ilegal, no puede pasarse de pendiente de recogida a en reparto)
        parcelsService.updateParcelStatus(parcelsService.getParcel(u1_to_u2_uuid), ParcelStatus.IN_DELIVERY);

        // User2 -> User1 con recogida a domicilio (REMOTE)
        UUID u2_to_u1_uuid = parcelsService.registerNewParcel(user2, user1, 120.0, 190.0, 140.0);
        parcelsService.processParcelPickupOrder(parcelsService.getParcel(u2_to_u1_uuid), ParcelPickupOrderType.REMOTE);

        log.info("Sample data successfully inserted");
    }
}