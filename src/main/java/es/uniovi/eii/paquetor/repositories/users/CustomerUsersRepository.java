package es.uniovi.eii.paquetor.repositories.users;

import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerUsersRepository extends UsersRepository<CustomerUser> {
}