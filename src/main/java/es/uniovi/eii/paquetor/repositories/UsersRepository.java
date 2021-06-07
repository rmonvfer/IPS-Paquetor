package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.users.BaseUser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UsersRepository extends CrudRepository<BaseUser, UUID> {
    BaseUser findByEmail(String email);
}