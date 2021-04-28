package es.uniovi.paquetor.repositories;

import es.uniovi.paquetor.entities.Empleado;
import org.springframework.data.repository.CrudRepository;

public interface EmpleadoRepository extends CrudRepository<Empleado, Long> {
}