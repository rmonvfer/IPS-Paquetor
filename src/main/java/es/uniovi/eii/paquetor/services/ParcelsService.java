package es.uniovi.eii.paquetor.services;
import es.uniovi.eii.paquetor.entities.RouteStopType;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.parcels.ParcelPickupOrderType;
import es.uniovi.eii.paquetor.entities.parcels.ParcelStatus;
import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import es.uniovi.eii.paquetor.repositories.ParcelsRepository;
import es.uniovi.eii.paquetor.repositories.WarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ParcelsService {

    @Autowired
    ParcelsRepository parcelsRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    WarehouseService warehouseService;

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
    public UUID registerNewParcel(CustomerUser sender, CustomerUser recipient, Double height, Double width, Double depth) {
        UUID parcelUUID = UUID.randomUUID();
        Parcel newParcel =
                new Parcel(sender, recipient).setId(parcelUUID).setDepth(depth)
                        .setHeight(height).setWidth(width).setStatus(ParcelStatus.NOT_PROCESSED);
        parcelsRepository.save(newParcel);

        log.debug("Registrado nuevo paquete en el sistema " + newParcel);

        return parcelUUID;
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
        CustomerUser sender    = parcel.getSender();
        CustomerUser recipient = parcel.getRecipient();

        log.debug("Procesando orden de recogida para el paquete " + parcel);

        // Buscar el almacén correspondiente a esa ubicación
        Warehouse senderReferenceWarehouse =
                warehouseRepository.findByCiudadIgnoreCase(sender.getLocation().getCiudad());

        // Si el cliente ha solicitado una recogida
        if (pickupOrderType == ParcelPickupOrderType.REMOTE) {
            // Marcar el paquete como pendiente de recogida.
            updateParcelStatus(parcel, ParcelStatus.PICKUP_PENDING);

            // Añadir una parada de recogida en la ruta interna del almacén de referencia para el emisor
            warehouseService.addParcelToInternalRoute(parcel, RouteStopType.PICKUP);

            log.debug("Orden de recogida registrada correctamente.");
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
        parcel.setStatus(parcelStatus);
        parcelsRepository.save(parcel);
    }

    public List<Parcel> getCustomerParcels(CustomerUser customer) {
        parcelsRepository.find
    }

    public Parcel getParcel(UUID id) {
        return parcelsRepository.findById(id).get();
    }

    public void deleteParcel(UUID id) {
        parcelsRepository.deleteById(id);
    }
}
