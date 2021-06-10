package es.uniovi.eii.paquetor.controllers;

import es.uniovi.eii.paquetor.entities.users.CustomerUser;
import es.uniovi.eii.paquetor.services.ParcelsService;
import es.uniovi.eii.paquetor.services.RolesService;
import es.uniovi.eii.paquetor.services.SecurityService;
import es.uniovi.eii.paquetor.services.UsersService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    SecurityService securityService;

    @Autowired
    RolesService rolesService;

    @Autowired
    ParcelsService parcelsService;

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
        model.addAttribute("user", new CustomerUser());
        return "register";
    }

    @PostMapping(value = "/register")
    public String signup(@ModelAttribute("user") CustomerUser user, Model model) {

        log.info("Attempting to register a new User with email="+user.getEmail());

        usersService.addCustomer(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @GetMapping("/home")
    public String userHome(Model model) {
        CustomerUser currentCustomer = usersService.getLoggedInUser();
        model.addAttribute("sentParcels", parcelsService.getCustomerSentParcels(currentCustomer));
        model.addAttribute("receivedParcels", parcelsService.getCustomerReceivedParcels(currentCustomer));
        return "home";
    }
}