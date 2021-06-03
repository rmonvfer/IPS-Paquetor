package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UsersService {

    @Autowired
    UsersRepository usersRepository;

}
