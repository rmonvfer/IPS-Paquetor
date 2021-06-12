package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.repositories.UsersRepository;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RolesService rolesService;

    @PostConstruct
    public void init() { /**/ }

    /**
     * Obtiene un listado de todos los usuarios del sistema: empleados y clientes.
     * @return Lista con los usuarios del sistema.
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Obtiene un cliente por su id
     * @param id UUID del cliente
     * @return cliente si se encuentra, null en caso contrario.
     */
    public User getCustomer(UUID id) {
        return usersRepository.findById(id).get();
    }

    /**
     * Registra un cliente.
     * @param user Cliente a registrar
     */
    public void addCustomer(User user) {
        log.error("Adding customer with values: " + user);
        user.setRole(rolesService.getRoles()[0]);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPasswordConfirm()));
        user.setId(UUID.randomUUID());
        usersRepository.save(user);
    }

    /**
     * Obtiene a un cliente identificado por su email
     * @param email email del cliente
     * @return cliente si se encuentra, null en caso contrario.
     */
    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    /**
     * Obtiene al usuario que ha iniciado sesión el el sistema.
     * @return Usuario.
     */
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return getUserByEmail(auth.getName());
    }

    public void deleteCustomer(UUID id) {
        usersRepository.deleteById(id);
    }

    public void editCustomer(User user) {
        usersRepository.save(user);
    }
}
