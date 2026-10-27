package com.ticketflow.customerservice.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@RefreshScope
public class ConfigTestController {

    @Value("${name}")
    String prenom;

    @Value("${school}")
    String ecole;

    @GetMapping
    String getConfigTest(){
        return "prenom: " + prenom + " | " + "ecole: " + ecole;
    }
}
