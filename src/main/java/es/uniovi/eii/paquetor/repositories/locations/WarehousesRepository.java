package es.uniovi.eii.paquetor.repositories.locations;

import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import es.uniovi.eii.paquetor.repositories.locations.LocationsRepository;

public interface WarehousesRepository extends LocationsRepository<Warehouse> {
    Warehouse findByCiudadIgnoreCase(String ciudad);
}