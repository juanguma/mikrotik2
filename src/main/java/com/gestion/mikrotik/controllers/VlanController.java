package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.VlanService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Controller

public class VlanController {
    @Autowired
    public VlanService vlanService;
    @Autowired
    public IpAddressService ipAddressService;
    @PostMapping("/addvlan")
    public String createVlan (Vlan newVlan) {
       try{
           this.vlanService.addVlan(newVlan);
           return "redirect:/showvlans";
       } catch (Exception e) {//pendiente enviar errores con este catch
           return "redirect:/showvlans";
       }

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
         return "addvlans";

    }

    @GetMapping("/updatevlan/{id}")
    public String updateVlan(@NotNull Model model, @PathVariable int id ){
        Vlan vlan = vlanService.getVlanById(id);
        model.addAttribute("vlan",vlan);
        return "updatevlan";
    }
    @PostMapping("/updatevlan/{id}")
    public String updateVlan (Vlan vlan,@PathVariable int id) throws UnknownHostException {

       Vlan currentVlan = this.vlanService.getVlanById(id);
        System.out.println(currentVlan.getVlanRef()+"------------------------------->");
        currentVlan.setVlanName(vlan.getVlanName());
        currentVlan.setNetworkAddress(vlan.getNetworkAddress());
        InetAddress inet = InetAddress.getByName(currentVlan.getNetworkAddress());
        currentVlan.setNetworkHashcode((inet.hashCode()));
        System.out.println(vlan.getVlanName());
        System.out.println(vlan.getNetworkAddress());
        System.out.println(vlan.getId());
        System.out.println(vlan.getVlanRef());
        this.vlanService.updateVlan(currentVlan);
        return "redirect:/showvlans";
    }
    @GetMapping("/showip/{vlanid}")
    public String showipbyvlan (@PathVariable int vlanid, Model model ){
        List<IpAddress>  ipList= this.ipAddressService.getAllIpByVlan(vlanid);
        model.addAttribute("ipList",ipList);

        return "showip";
    }



    }



