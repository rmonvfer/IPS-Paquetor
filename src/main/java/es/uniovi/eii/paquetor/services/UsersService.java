package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.users.BaseUser;
import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import es.uniovi.eii.paquetor.repositories.users.CustomerUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    CustomerUsersRepository customersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() { /**/ }

    /**
     * Obtiene un listado de todos los usuarios del sistema: empleados y clientes.
     * @return Lista con los usuarios del sistema.
     */
    public List<BaseUser> getUsers() {
        List<BaseUser> users = new ArrayList<>();
        customersRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Obtiene un cliente por su id
     * @param id UUID del cliente
     * @return cliente si se encuentra, null en caso contrario.
     */
    public CustomerUser getCustomer(UUID id) {
        return customersRepository.findById(id).get();
    }

    /**
     * Registra un cliente.
     * @param user Cliente a registrar
     */
    public void addCustomer(CustomerUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID());
        customersRepository.save(user);
    }

    /**
     * Obtiene a un cliente identificado por su email
     * @param email email del cliente
     * @return cliente si se encuentra, null en caso contrario.
     */
    public CustomerUser getUserByEmail(String email) {
        return (CustomerUser) customersRepository.findByEmail(email);
    }

    /**
     * Obtiene al usuario que ha iniciado sesión el el sistema.
     * @return Usuario.
     */
    public CustomerUser getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return getUserByEmail(auth.getName());
    }

    public void deleteCustomer(UUID id) {
        customersRepository.deleteById(id);
    }

    public void editCustomer(CustomerUser user) {
        customersRepository.save(user);
    }
}
