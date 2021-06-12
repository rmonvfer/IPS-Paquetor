package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.RouteStop;
import es.uniovi.eii.paquetor.entities.RouteStopType;
import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.repositories.LocationsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service @Log4j2
public class WarehousesService {

    @Autowired
    LocationsRepository locationsRepository;

    /**
     * Añade un paquete a la ruta interna del almacén ubicado en la misma
     * ciudad del emisor del paquete.
     * @param parcel paquete a añadir.
     */
    public void addParcelToInternalRoute(Parcel parcel, RouteStopType stopType) {
        log.info("Adding Parcel to Warehouse internal route");

        User sender = parcel.getSender();
        Warehouse senderReferenceWarehouse =
                locationsRepository.findByCiudadIgnoreCase(sender.getLocation().getCiudad());
        log.info("Reference Warehouse found in " + sender.getLocation().getCiudad());

        // Crear una parada de ruta e introducir el paquete y el tipo de parada
        RouteStop customerRouteStop = new RouteStop(parcel, stopType);
        senderReferenceWarehouse.getInternalRoute().getRouteStops().add(customerRouteStop);
        log.info("A RouteStop has been added to the previously found reference warehouse");

        locationsRepository.save(senderReferenceWarehouse);
        log.info("Parcel added to internal route successfully!");
    }

    public Warehouse findByCiudad(String ciudad) {
        return locationsRepository.findByCiudadIgnoreCase(ciudad);
    }
}
