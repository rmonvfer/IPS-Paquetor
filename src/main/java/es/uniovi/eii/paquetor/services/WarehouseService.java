package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.Route;
import es.uniovi.eii.paquetor.entities.RouteStop;
import es.uniovi.eii.paquetor.entities.RouteStopType;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import es.uniovi.eii.paquetor.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    /**
     * Añade un paquete a la ruta interna del almacén ubicado en la misma
     * ciudad del emisor del paquete.
     * @param parcel paquete a añadir.
     */
    public void addParcelToInternalRoute(Parcel parcel, RouteStopType stopType) {
        CustomerUser sender = parcel.getSender();
        Warehouse senderReferenceWarehouse =
                warehouseRepository.findByCiudadIgnoreCase(sender.getLocation().getCiudad());

        // Crear una parada de ruta e introducir el paquete y el tipo de parada
        RouteStop customerRouteStop = new RouteStop(parcel, stopType);
        senderReferenceWarehouse.getInternalRoute().getRouteStops().add(customerRouteStop);
        warehouseRepository.save(senderReferenceWarehouse);
    }
}
