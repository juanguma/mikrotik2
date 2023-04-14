package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.MikrotikService;
import com.gestion.mikrotik.services.VlanService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static com.gestion.mikrotik.utilidades.pruebas.num2Ip;

@Controller
public class IpAddressController {
    @Autowired
    public IpAddressService ipService;
    @Autowired
    public VlanService vlanService;

    @Autowired
    public MikrotikService mikroService;

    @SneakyThrows
    @GetMapping("/buscarip/{id}")
    public String buscarips(@PathVariable int id){
        Vlan vlan =vlanService.getVlanById(id);
        for (int i = vlan.getNetworkHashcode(); i < vlan.getNetworkHashcode()+255 ; i++) {
            String direccionIP=num2Ip(i);
            if(InetAddress.getByName(direccionIP).isReachable(2000)){
                if(this.ipService.findIpAddress(direccionIP)){
                    System.out.println("la direccion existe ");

                }else{
                    IpAddress newIP= new IpAddress(vlan,i,direccionIP);

                    this.ipService.addAddress(newIP);
                    System.out.println("la direccion "+direccionIP+" inseratada");
                }

            }else{
                System.out.println("la direccion "+direccionIP+" Responde NO  Ping");
            }
        }

        return "redirect:/showip/{id}";
    }

@GetMapping("/listaip/{id}")
public List<IpAddress> listarIpByVlan(@PathVariable  int id, Model model){
       return  this.ipService.getAllIpByVlan(id);

}

@GetMapping("/findip/{ipaddress}")
public String findByipaddress(Model model, @PathVariable String ipAddress){
    System.out.println("ok");
    List<IpAddress> ipList = this.ipService.findByIpAdresss(ipAddress);
    model.addAttribute("ipList",ipList);


    return "showip";
}
    public IpAddressController(IpAddressService ipService) {
        this.ipService = ipService;
    }


    @GetMapping("/searchip")
    public String submissionResult( @RequestParam String ipaddress, Model model ) {
        List<IpAddress> ipList = this.ipService.findByIpAdresss(ipaddress);
        model.addAttribute("ipList",ipList);
        return "showip";
    }

 @GetMapping("/buscarips")
    public String buscarips() throws IOException {
     System.out.println("ok");
     List<Vlan> vlanList = this.vlanService.getAllVlan();

     for (Vlan vlan: vlanList){
         for (int i = vlan.getNetworkHashcode(); i < vlan.getNetworkHashcode()+255 ; i++) {
             String direccionIP=num2Ip(i);
             if(InetAddress.getByName(direccionIP).isReachable(2000)){
                 if(this.ipService.findIpAddress(direccionIP)){
                     System.out.println("la direccion existe ");

                 }else{
                     IpAddress newIP= new IpAddress(vlan,i,direccionIP);

                     this.ipService.addAddress(newIP);
                     System.out.println("la direccion "+direccionIP+" inseratada");
                 }

             }else{
                 System.out.println("la direccion "+direccionIP+" Responde NO  Ping");
             }
         }

     }

     return "ok";

 }

}
