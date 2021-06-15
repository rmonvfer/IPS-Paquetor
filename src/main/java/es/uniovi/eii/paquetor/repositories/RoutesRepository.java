package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.routes.Route;
import org.springframework.data.repository.CrudRepository;

public interface RoutesRepository extends CrudRepository<Route, Long> {
}