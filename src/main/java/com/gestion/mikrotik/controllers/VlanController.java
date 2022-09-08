package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.IpAddress;
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
import org.springframework.web.bind.annotation.*;

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
        return "redirect:/showvlans";
    }

     @GetMapping({"/showvlans","/addvlan"})//Aca se listan las vlan Creadas
    public String showvlans(@NotNull Model model ){
    List<Vlan> listVlan= this.vlanService.getAllVlan();
    model.addAttribute("vlanlist",listVlan);
        return "showvlans";
     }

    @GetMapping("/addvlans")
    public String addvlans(@NotNull Model model){
        Vlan newVlan= new Vlan();
        model.addAttribute("newVlan",newVlan);
         return "/addvlans";

    }

    @GetMapping("/updatevlan/{id}")
    public String updateVlan(@NotNull Model model, @PathVariable int id ){
        Vlan vlan = vlanService.getVlanById(id);
        model.addAttribute("vlan",vlan);
        return "updatevlan";
    }
    @PostMapping("/updatevlan/{id}")
    public String updateVlan (Vlan vlan,@PathVariable int id) {
        this.vlanService.updateVlan(vlan,id);
        return "redirect:/showvlans";
    }

    public void listarips(int vlanid){////pendiente
        Vlan vlan = this.vlanService.getVlanById(vlanid);
        List<IpAddress> listaIp;

    }



    }



