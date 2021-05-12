package es.uniovi.paquetor.controllers;

import es.uniovi.paquetor.entities.Usuario;
import es.uniovi.paquetor.services.RolesService;
import es.uniovi.paquetor.services.SecurityService;
import es.uniovi.paquetor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RolesService rolesService;

    // Registro: GET
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new Usuario());
        return "signup";
    }

    // Registro: POST
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(Usuario user) {
        user.setRole(rolesService.getRoles().get("USER"));
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getTempPassword());
        return "redirect:home";
    }

    // Inicio de sesión: GET
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    // Página principal del usuario
    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario activeUser = usersService.getUserByEmail(email);
        model.addAttribute("listaPaquetesEnviados", activeUser.getPaquetes_enviados());
        model.addAttribute("listaPaquetesRecibidos", activeUser.getPaquetes_recibidos());
        return "home";
    }
}