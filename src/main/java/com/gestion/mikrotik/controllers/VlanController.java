package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.services.VlanService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Controller

public class VlanController {
    @Autowired
    public VlanService vlanService;
    @PostMapping("/addvlan")
    public String createVlan (Vlan newVlan) {
        this.vlanService.addVlan(newVlan);
        return "/showvlans";
    }

     @GetMapping({"/showvlans","/addvlan"})//Aca se listan las vlan Creadas
    public String showvlans( Model model ){
    List<Vlan> listVlan= this.vlanService.getAllVlan();
    model.addAttribute("vlanlist",listVlan);
        return "showvlans";
     }

    @GetMapping("/addvlans")
    public String addvlans(Model model){
        Vlan newVlan= new Vlan();
        model.addAttribute("newVlan",newVlan);
         return "addvlans";
    }

    }



