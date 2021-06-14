package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.locations.City;
import org.springframework.data.repository.CrudRepository;

public interface CitiesRepository extends CrudRepository<City, Long> {
}