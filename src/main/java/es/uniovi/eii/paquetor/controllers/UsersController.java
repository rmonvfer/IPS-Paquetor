package es.uniovi.eii.paquetor.controllers;

import es.uniovi.eii.paquetor.entities.users.User;
import es.uniovi.eii.paquetor.services.RolesService;
import es.uniovi.eii.paquetor.services.SecurityService;
import es.uniovi.eii.paquetor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    SecurityService securityService;

    @Autowired
    RolesService rolesService;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(value = "/register")
    public String signup(@ModelAttribute("user") User user, Model model) {
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @GetMapping("/home")
    public String userHome(Model model) {
        return "home";
    }
}

