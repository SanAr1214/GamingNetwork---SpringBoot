package com.GN.Gaming.Network.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {

    @GetMapping("/")
    public String home() {
        return "Bienvenido a mi primera app con Spring Boot 🚀";
    }

    @GetMapping("/hola")
    public String hola() {
        return "¡Hola desde Spring Boot!";
    }
}