package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.services.ClientsService;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.MikrotikService;
import com.gestion.mikrotik.services.VlanService;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller

public class ClientController {
    @Autowired
    public IpAddressService ipService;
    @Autowired
    public VlanService vlanService;

    @Autowired
    public MikrotikService mikroService;
    @Autowired
    public ClientsService clientsService;

    @GetMapping("/showclients")
    public String  showclient(){
        return "showclients";
    }

    @GetMapping("/findclients")
    public String findclients() throws MikrotikApiException {
        List<Map<String, String>> clientList = this.clientsService.findClientswir("10.0.0.10");
        for (int i = 0; i < clientList.size(); i++) {

            System.out.println("___________________________________________________________________________________________________");
            String mac= clientList.get(i).get("mac-address");
            String nameClient= clientList.get(i).get("comment");
            String active= clientList.get(i).get("disabled");
            System.out.println(mac+nameClient+active);



            System.out.println("___________________________________________________________________________________________________");

        }
        return "ok";
    }




}
