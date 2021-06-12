package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ParcelsRepository extends CrudRepository<Parcel, UUID> {
    @Query("SELECT p FROM Parcel p WHERE p.sender = ?1")
    List<Parcel> findAllSentByUser(User customer);

    @Query("SELECT p FROM Parcel p WHERE p.recipient = ?1")
    List<Parcel> findAllReceivedByUser(User customer);
}