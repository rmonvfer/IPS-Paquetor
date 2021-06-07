package es.uniovi.eii.paquetor.services;
import es.uniovi.eii.paquetor.entities.RouteStopType;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.parcels.ParcelPickupOrderType;
import es.uniovi.eii.paquetor.entities.parcels.ParcelStatus;
import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import es.uniovi.eii.paquetor.repositories.ParcelsRepository;
import es.uniovi.eii.paquetor.repositories.WarehouseRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
        }
    }

    public void updateParcelStatus(Parcel parcel, ParcelStatus parcelStatus) {
        parcel.setStatus(parcelStatus);
        parcelsRepository.save(parcel);
    }

    public Parcel getParcel(Long id) {
        return parcelsRepository.findById(id).get();
    }

    public void addParcel(Parcel parcel) {
        parcelsRepository.save(parcel);
    }

    public void deleteParcel(Long id) {
        parcelsRepository.deleteById(id);
    }

    public void editParcel(Parcel parcel) {
        parcelsRepository.save(parcel);
    }
}
