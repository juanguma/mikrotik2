package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.services.MikrotikService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MikrotikController {

    MikrotikService service;

    public MikrotikController(MikrotikService service){
        this.service=service;
    }

    @GetMapping("/mikrotik")
    public List<Mikrotik> mostrarMikrotik(){
        return service.getMikrotik();
    }
    /*
    @PostMapping("/mikrotik")
    public Mikrotik createMikrotik(@RequestBody){
    }*/

}
