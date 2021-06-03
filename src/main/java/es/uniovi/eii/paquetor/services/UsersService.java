package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @PostConstruct
    public void init() { /**/ }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public void addUser(User user) {
        // user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public void editUser(User user) {
        usersRepository.save(user);
    }
}
