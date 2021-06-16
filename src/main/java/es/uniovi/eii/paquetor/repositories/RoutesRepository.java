package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.locations.City;
import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.entities.routes.Route;
import es.uniovi.eii.paquetor.entities.routes.RouteStopType;
import es.uniovi.eii.paquetor.entities.routes.RouteType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoutesRepository extends CrudRepository<Route, Long> {
    List<Route> findByRouteStops_Location_CityEqualsAndRouteTypeEquals(City city, RouteType routeType);
}