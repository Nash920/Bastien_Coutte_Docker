package com.ingnum.rentalservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BonjourController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/bonjour")
    public String bonjour() {
        return "bonjour";
    }

    @GetMapping("/bonjour-php")
    public String bonjourDepuisPhp() {
        // Appel du microservice PHP via le nom de service docker-compose
        String prenom = restTemplate.getForObject("http://php-service", String.class);
        return "bonjour " + prenom;
    }
}
