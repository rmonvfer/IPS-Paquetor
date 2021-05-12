package es.uniovi.paquetor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@wController
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}