package es.uniovi.eii.paquetor.controllers;

import es.uniovi.eii.paquetor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {
    
    @Autowired
    UsersService usersService;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }
}

