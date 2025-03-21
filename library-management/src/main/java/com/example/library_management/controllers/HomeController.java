package com.example.library_management.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "¡Bienvenido a la API de Library Management!";
    }
}
