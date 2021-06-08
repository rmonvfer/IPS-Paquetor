package es.uniovi.eii.paquetor.repositories.users;

import es.uniovi.eii.paquetor.entities.users.BaseUser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UsersRepository<T extends BaseUser> extends CrudRepository<T, UUID> {
    T findByEmail(String email);
}