package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UsersRepository extends CrudRepository<User, UUID> {
    User findByEmail(String email);
}