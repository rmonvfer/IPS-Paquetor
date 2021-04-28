package es.uniovi.paquetor.controllers;

import es.uniovi.paquetor.entities.Usuario;
import es.uniovi.paquetor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping("/user/add")
    public String getUser(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute Usuario user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        Usuario user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new Usuario());
        return "signup";
    }

    // Registro de un usuario
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(Usuario user) {
        usersService.addUser(user);
        // securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //String dni = auth.getName();
        Usuario activeUser = usersService.getUserByEmail("");
        model.addAttribute("listaPaquetesEnviados", activeUser.getPaquetes());
        model.addAttribute("listaPaquetesRecibidos", activeUser.getPaquetes_recibidos());
        return "home";
    }
}