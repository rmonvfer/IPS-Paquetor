package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.users.BaseUser;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<BaseUser, Long> {
    BaseUser findByEmail(String email);
}