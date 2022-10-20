package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.MikrotikService;
import com.gestion.mikrotik.services.VlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

public class ClientController {
    @Autowired
    public IpAddressService ipService;
    @Autowired
    public VlanService vlanService;

    @Autowired
    public MikrotikService mikroService;

    @GetMapping("/showclients")
    public String  showclient(){
        return "showclients";
    }


}
