package es.uniovi.eii.paquetor.controllers;

import es.uniovi.eii.paquetor.dtos.ParcelStatusUpdateDTO;
import es.uniovi.eii.paquetor.entities.parcels.Parcel;
import es.uniovi.eii.paquetor.entities.parcels.ParcelPickupOrderType;
import es.uniovi.eii.paquetor.services.ParcelsService;
import es.uniovi.eii.paquetor.services.UsersService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@Log4j2
public class ParcelsController {

    @Autowired
    ParcelsService parcelsService;

    @Autowired
    UsersService usersService;

    @GetMapping("/parcel/register")
    public String showParcelRegistrationForm(Model model) {
        model.addAttribute("parcel", new Parcel());
        return "parcels/registerParcel";
    }

    @PostMapping(value = "/parcel/register")
    public String registerParcel(@ModelAttribute("parcel") Parcel parcel, Model model) {
        parcel.setSender(usersService.getLoggedInUser());
        UUID new_parcel_uuid = parcelsService.registerNewParcel(parcel);

        parcelsService.processParcelPickupOrder(
                parcelsService.getParcel(new_parcel_uuid), ParcelPickupOrderType.REMOTE);

        model.addAttribute("newParcel", parcelsService.getParcel(new_parcel_uuid));
        return "parcels/newParcelRegistered";
    }

    @GetMapping({"/parcel/detail"})
    public String getParcelDetail(@RequestParam(required = false) String uuid, Model model){
        if (uuid != null) {
            Parcel foundParcel = parcelsService.getParcel(UUID.fromString(uuid));
            if (foundParcel != null) {
                model.addAttribute("foundParcel", foundParcel);
                return "parcels/parcelDetails";
            }
        }
        return "parcels/parcelTrackingForm";
    }
}
