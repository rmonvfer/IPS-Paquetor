package es.uniovi.paquetor.services;

import es.uniovi.paquetor.entities.Usuario;
import es.uniovi.paquetor.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsuarioRepository usersRepository;

    @PostConstruct
    public void init() { }

    public List<Usuario> getUsers() {
        List<Usuario> users = new ArrayList<Usuario>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public Usuario getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public void addUser(Usuario user) {
        usersRepository.save(user);
    }

    public Usuario getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public void editUser(Usuario user) {
        usersRepository.save(user);
    }
}