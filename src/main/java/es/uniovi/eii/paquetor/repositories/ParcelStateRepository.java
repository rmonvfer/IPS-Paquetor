package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.parcels.ParcelState;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ParcelStateRepository extends CrudRepository<ParcelState, UUID> {
}