package es.uniovi.eii.paquetor.services;
import es.uniovi.eii.paquetor.entities.RouteStopType;
import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.parcels.ParcelPickupOrderType;
import es.uniovi.eii.paquetor.entities.parcels.ParcelState;
import es.uniovi.eii.paquetor.entities.parcels.ParcelStatus;
import es.uniovi.eii.paquetor.repositories.ParcelStateRepository;
import es.uniovi.eii.paquetor.repositories.ParcelsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ParcelsService {

    @Autowired
    ParcelsRepository parcelsRepository;

    @Autowired
    WarehousesService warehousesService;

    @Autowired
    LocationsService locationsService;

    @Autowired
    ParcelStateRepository parcelStateRepository;

    @Autowired
    UsersService usersService;

    @PostConstruct
    public void init() { /**/ }

    public List<Parcel> getParcels() {
        List<Parcel> parcels = new ArrayList<>();
        parcelsRepository.findAll().forEach(parcels::add);
        return parcels;
    }

    /**
     * Registra un nuevo paquete en el sistema.
     * @param sender Usuario que envía el paquete
     * @param recipient Usuario que recibirá el paquete
     * @param height Altura del paquete
     * @param width Ancho del paquete
     * @param depth Profundidad del paquete
     * @return UUID, identificador único aleatorio del paquete.
     */
    public UUID registerNewParcel(User sender, User recipient, Double weight, Double height, Double width, Double depth) {
        UUID parcelUUID = UUID.randomUUID();
        log.info(String.format("Registering a new Parcel {UUID=%s, sender=%s, recipient=%s, h=%f, w=%f, d=%f}",
                parcelUUID, sender, recipient, height, width, depth));

        // Obtener los datos del usuario de la base de datos (los datos recibidos son del formulario)
        User realRecipient = usersService.getUserByEmail(recipient.getEmail());

        // Si hay un usuario registrado con ese email, usarlo.
        if (realRecipient != null) {
            recipient = realRecipient;
            log.info("Parcel recipient already exists, it's " + realRecipient);

        // Si no, entonces crear un usuario deshabilitado (no puede iniciar sesión)
        } else {
            // Registrar también su domicilio.
            locationsService.addLocation(recipient.getLocation());
            usersService.addDisabledCustomer(recipient);
            log.info("Parcel recipient doesn't exist, a temporary one has been created");
        }

        Parcel newParcel = new Parcel(sender, recipient)
                .setId(parcelUUID).setDepth(depth).setHeight(height).setWidth(width).setWeight(weight);

        // Establecer el estado inicial como "No procesado"
        updateParcelStatus(newParcel, ParcelStatus.NOT_PROCESSED);
        parcelsRepository.save(newParcel);

        log.info("Parcel with UUID " + parcelUUID + " has been registered successfully!");
        return parcelUUID;
    }

    /**
     * Registra un nuevo paquete recibido como parámetro.
     * Añade un UUID y establece su estado a NOT_PROCESSED
     * @param parcel paquete a añadir
     * @return ID del nuevo paquete registrado
     */
    public UUID registerNewParcel(Parcel parcel) {
        return registerNewParcel(
                parcel.getSender(), parcel.getRecipient(),
                parcel.getWeight(), parcel.getHeight(), parcel.getWidth(), parcel.getDepth());
    }

    /**
     * Procesa una petición de recogida para un paquete. Esta etapa es común a los dos tipos de envío,
     * interno y externo.
     * Introduce el paquete en la ruta de recogida del almacén de referencia (el que se encuentre en la misma
     * localidad del emisor).
     * Una vez que el paquete se encuentre en el almacén, el transportista será el que lo cargue en el transporte
     * requerido para ser enviado a otra ciudad (mediante una ruta externa).
     * @param parcel paquete a procesar
     * @param pickupOrderType tipo de orden de recogida.
     */
    public void processParcelPickupOrder(Parcel parcel, ParcelPickupOrderType pickupOrderType) {

        log.info("Received a ParcelPickupOrder for Parcel " + parcel);

        // Si el cliente ha solicitado una recogida
        if (pickupOrderType == ParcelPickupOrderType.REMOTE) {
            log.info("Sender has requested a REMOTE pickup");

            // Marcar el paquete como pendiente de recogida.
            updateParcelStatus(parcel, ParcelStatus.PICKUP_PENDING);

            // Añadir una parada de recogida en la ruta interna del almacén de referencia para el emisor
            warehousesService.addParcelToInternalRoute(parcel, RouteStopType.PICKUP);

            log.info("ParcelPickupOrder successfully processed!");
        }
    }

    /**
     * Actualiza el estado de un paquete (equivalente a "marcarlo")
     * @param parcel paquete a actualizar
     * @param parcelStatus nuevo estado
     */
    //TODO: Impedir cambios de estado aleatorios, desde un estado concreto solo debe poder 
    //      cambiarse a una serie concreta y limitada de estados.
    public void updateParcelStatus(Parcel parcel, ParcelStatus parcelStatus) {
        UUID newParcelStateUUID = UUID.randomUUID();
        ParcelState newParcelState = new ParcelState().setId(newParcelStateUUID)
                .setStatus(parcelStatus).setUpdated_date(new Date());
        parcelStateRepository.save(newParcelState);
        parcel.getStatesRecord().add(parcelStateRepository.findById(newParcelStateUUID).get());

        log.info("Updating parcel status with " + parcelStateRepository.findById(newParcelStateUUID).get());
        parcelsRepository.save(parcel);
    }

    public List<Parcel> getCustomerSentParcels(User customer) {
        return parcelsRepository.findAllSentByUser(customer);
    }

    public List<Parcel> getCustomerReceivedParcels(User customer) {
        return parcelsRepository.findAllReceivedByUser(customer);
    }

    public Parcel getParcel(UUID id) {
        log.info("Attempting to get Parcel with uuid="+ id);
        return parcelsRepository.findById(id).get();
    }

    public void deleteParcel(UUID id) {
        parcelsRepository.deleteById(id);
    }
}
