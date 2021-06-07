package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.users.BaseUser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LocationsRepository<T extends Location> extends CrudRepository<T, UUID> {
    Warehouse findByCiudadIgnoreCase(String ciudad);
}