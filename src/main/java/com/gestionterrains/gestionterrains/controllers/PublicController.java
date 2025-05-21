package com.gestionterrains.gestionterrains.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/terrains")
    public String terrains() {
        return "public/terrains";
    }

    @GetMapping("/reservations")
    public String reservations() {
        return "public/reservations";
    }
}