package es.uniovi.paquetor.repositories;

import es.uniovi.paquetor.entities.Paquete;
import org.springframework.data.repository.CrudRepository;

public interface PaqueteRepository extends CrudRepository<Paquete, Long> {
}