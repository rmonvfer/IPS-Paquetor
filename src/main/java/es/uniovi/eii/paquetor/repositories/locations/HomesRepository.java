package es.uniovi.eii.paquetor.repositories.locations;

import es.uniovi.eii.paquetor.entities.locations.Home;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface HomesRepository extends LocationsRepository<Home> {
}