package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import org.springframework.data.repository.CrudRepository;

public interface ParcelsRepository extends CrudRepository<Parcel, Long> { /**/ }