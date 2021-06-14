package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.locations.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationsRepository extends CrudRepository<Location, Long> {
    Optional<Location> findByCity_NameEqualsIgnoreCase(String name);
}