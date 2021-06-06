package es.uniovi.eii.paquetor.services;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import es.uniovi.eii.paquetor.repositories.ParcelsRepository;
import es.uniovi.eii.paquetor.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ParcelsService {

    @Autowired
    ParcelsRepository parcelsRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @PostConstruct
    public void init() { /**/ }

    public List<Parcel> getParcels() {
        List<Parcel> parcels = new ArrayList<>();
        parcelsRepository.findAll().forEach(parcels::add);
        return parcels;
    }

    public int sendParcel(Parcel parcel) {
        CustomerUser sender    = parcel.getSender();
        CustomerUser recipient = parcel.getRecipient();

        // Buscar el almacén correspondiente a esa ubicación
        Warehouse senderReferenceWarehouse =
                warehouseRepository.findByCiudadIgnoreCase(sender.getLocation().getCiudad());

        // Reparto interno (emisor y receptor están en la misma ciudad)
        if (sender.getLocation().getCiudad().equalsIgnoreCase(recipient.getLocation().getCiudad())) {
            // Añadir el paquete a la ruta de reparto interna.
            senderReferenceWarehouse.getInternalRoute().getRouteStops().add()
        }

        // Añadir el paquete a la ruta d


        return 0;
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
