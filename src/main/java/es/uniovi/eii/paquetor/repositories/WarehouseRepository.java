package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.locations.Warehouse;
import org.springframework.data.repository.CrudRepository;

public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {
    Warehouse findByCiudadIgnoreCase(String ciudad);
}