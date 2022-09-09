package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.VlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IpAddRestController {

    @Autowired
    VlanService vlanService;
    @Autowired
    IpAddressService ipService;

    @GetMapping("/listarips")
    public List<IpAddress>listarips(){
        return this.ipService.getAllip();
    }

    @GetMapping("/listarips2/{id}")
    public List<IpAddress> listarbyvlan(@PathVariable int id){

        return  ipService.getAllIpByVlan(id);
    }


}
