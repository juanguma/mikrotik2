package com.gestion.mikrotik.controllers;

import ch.qos.logback.core.net.server.Client;
import com.gestion.mikrotik.entities.Clients;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.services.ClientsService;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.MikrotikService;
import com.gestion.mikrotik.services.VlanService;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    public String findclients() throws MikrotikApiException, IOException {
        List<Mikrotik> apList= this.mikroService.findMikrotikAP();

        for (Mikrotik ap:apList) {
            String node = ap.getIpAddresses().getIpAddress();
            if (InetAddress.getByName(node).isReachable(2000)) {
                List<Map<String, String>> clientList = ClientsService.findClientswir(node);
                System.out.println(ap.getName());
                if (clientList!=null){
                    for (int i = 0; i < clientList.size(); i++) {
                        Clients thisClient= new Clients();
                        thisClient.setMacAdresss(clientList.get(i).get("mac-address"));
                        thisClient.setName(clientList.get(i).get("comment"));
                        thisClient.setDisabledClient(Boolean.parseBoolean(clientList.get(i).get("disabled")));
                        thisClient.setNode(this.mikroService.findMikrotikByIp(node));
                        this.clientsService.saveClientDB(thisClient);
                    }
                }
            }
        }
        return "ok";
    }




}
