package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.locations.Location;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import org.springframework.data.repository.CrudRepository;

public interface LocationsRepository extends CrudRepository<Location, Long> {
    Location findByCiudadIgnoreCase(String ciudad);
}