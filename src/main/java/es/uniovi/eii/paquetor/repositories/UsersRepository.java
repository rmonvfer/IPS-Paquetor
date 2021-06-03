package es.uniovi.eii.paquetor.repositories;

import es.uniovi.eii.paquetor.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
