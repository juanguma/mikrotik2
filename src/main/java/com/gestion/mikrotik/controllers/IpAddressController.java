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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.InetAddress;
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
            if(InetAddress.getByName(direccionIP).isReachable(500)){
                IpAddress newIP= new IpAddress(vlan,i,direccionIP);
               this.ipService.addAddress(newIP);

                System.out.println("la direccion "+direccionIP+" inseratada");

            }else{
                System.out.println("la direccion "+direccionIP+" Responde NO  Ping");
            }
        }

        return "ok";
    }

@GetMapping("/listaip/{id}")
public List<IpAddress> listarIpByVlan(@PathVariable  int id, Model model){
       return  this.ipService.getAllIpByVlan(id);

}

    public IpAddressController(IpAddressService ipService) {
        this.ipService = ipService;
    }




}
