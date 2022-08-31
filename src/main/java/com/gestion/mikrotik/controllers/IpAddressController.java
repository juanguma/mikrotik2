package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.services.IpAdressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IpAddressController {
    public IpAdressService ipService;

    public IpAddressController(IpAdressService ipService) {
        this.ipService = ipService;
    }

    @GetMapping("/mostrarip")
    public List <IpAddress>showAllIp(){
        return ipService.getAllip();
    }
}
