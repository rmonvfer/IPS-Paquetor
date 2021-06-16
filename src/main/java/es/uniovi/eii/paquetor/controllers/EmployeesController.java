package es.uniovi.eii.paquetor.controllers;

import es.uniovi.eii.paquetor.dtos.ParcelStatusUpdateDTO;
import es.uniovi.eii.paquetor.entities.User;
import es.uniovi.eii.paquetor.entities.routes.Route;
import es.uniovi.eii.paquetor.services.ParcelsService;
import es.uniovi.eii.paquetor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class EmployeesController {

    @Autowired
    ParcelsService parcelsService;

    @Autowired
    UsersService usersService;

    @GetMapping({"/employee/home"})
    public String employeeHome(Model model) {
        User loggedInEmployee = usersService.getLoggedInUser();
        model.addAttribute("route", loggedInEmployee.getRoute());
        model.addAttribute("employee", loggedInEmployee);
        return "employees/home";
    }

    @GetMapping({"/employee/route/{route_id}/{route_stop_id}"})
    public String showRouteStepDetails(@PathVariable Long route_id, @PathVariable Long route_stop_id, Model model) {
        User loggedInEmployee = usersService.getLoggedInUser();
        model.addAttribute("route", loggedInEmployee.getRoute());
        return "employees/home";
    }

    @PostMapping({"/employee/parcel/update-status"})
    public String updateStatus(@ModelAttribute("psuDTO") ParcelStatusUpdateDTO psuDTO, Model model) {
        parcelsService.updateParcelStatus(
                parcelsService.getParcel(UUID.fromString(psuDTO.getParcelUUID())), psuDTO.getParcelStatus());
        return "";
    }
}
