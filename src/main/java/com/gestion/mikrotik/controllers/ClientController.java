package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.Clients;
import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.respositories.ClientsRepository;
import com.gestion.mikrotik.services.ClientsService;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.MikrotikService;
import com.gestion.mikrotik.services.VlanService;
import me.legrange.mikrotik.MikrotikApiException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
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
    @Autowired
    public ClientsRepository clientsRepo;
    @GetMapping("/showclients")
    public String  showclient(@NotNull Model model,@RequestParam(value="page",required = false,defaultValue = "0") int actualPage,
                              @RequestParam (value = "pagesize", required = false,defaultValue="50")int pageSize){
        Page<Clients> clientsPage = this.clientsRepo.findAll(PageRequest.of(actualPage,pageSize));

        int[] a = new int[clientsPage.getTotalPages()];
        System.out.printf(String.valueOf(actualPage));

        model.addAttribute("clientsList",clientsPage.getContent());
        model.addAttribute("totalPages", new int[clientsPage.getTotalPages()]);
        model.addAttribute("actualPage", actualPage);
        model.addAttribute("clientsPage", clientsPage);

     return "showclients";
    }

    @GetMapping("/netuser/findclients")
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


@GetMapping("/netuser/findclientsppp")
    public String finclientsppp(){
        Mikrotik node = this.mikroService.findMikrotikByIp("10.0.0.158");
        List<Map<String, String>> pppClients = this.clientsService.findClientsppp(node);
        System.out.println(pppClients.size());

    for (int i = 0; i < pppClients.size(); i++) {
        Clients thisClient = new Clients();
        thisClient.setIpadd(pppClients.get(i).get("remote-address"));
        thisClient.setName(pppClients.get(i).get("comment"));
        thisClient.setDisabledClient(Boolean.parseBoolean(pppClients.get(i).get("disabled")));
        thisClient.setClienttype("pppoe");
        thisClient.setIdentification( pppClients.get(i).get("password").substring(0, pppClients.get(i).get("password").length() - 1));
        thisClient.setNode(node);
        this.clientsService.saveClientDB(thisClient);
    }
        return "showclients";

}

@GetMapping ("/netuser/findclientsdhcp")
    public String findclientsdchp(){
    Mikrotik node = this.mikroService.findMikrotikByIp("10.13.1.109");
    List<Map<String, String>> dhcpClients = this.clientsService.findClientsdhcp(node);
    for (int i = 0; i <dhcpClients.size() ; i++) {

        Clients thisClient = new Clients();
        thisClient.setIpadd(dhcpClients.get(i).get("address"));
        thisClient.setName(dhcpClients.get(i).get("comment"));
        thisClient.setDisabledClient(Boolean.parseBoolean(dhcpClients.get(i).get("disabled")));
        thisClient.setClienttype("dhcp");
        thisClient.setNode(node);
        this.clientsService.saveClientDB(thisClient);

    }

    return "ok";
}

    @GetMapping("/showclients/{id}")
    public String  showclientByVlan(@NotNull Model model, @RequestParam(value="page",required = false,defaultValue = "0") int actualPage,
                                    @RequestParam (value = "pagesize", required = false,defaultValue="50")int pageSize, @PathVariable Long id ){

        Page<Clients> clientsPage = this.clientsRepo.findByNode_Id(id,PageRequest.of(actualPage,pageSize));
        List<Mikrotik> mikrotikList=this.mikroService.getMikrotik();
        int[] a = new int[clientsPage.getTotalPages()];
        System.out.printf(String.valueOf(actualPage));
        model.addAttribute("clientsList",clientsPage.getContent());
        model.addAttribute("totalPages", new int[clientsPage.getTotalPages()]);
        model.addAttribute("actualPage", actualPage);
        model.addAttribute("clientsPage", clientsPage);
        model.addAttribute("id",id.toString());
        model.addAttribute("mikrotikList",mikrotikList);

        return "showclientsid";
    }
    @Transactional
    @PostMapping ("/disableclient/{id}")
    public String disableClient(@PathVariable int id , HttpServletRequest request) throws MikrotikApiException {
        Clients client = this.clientsService.findClientById(id);
        String referer = request.getHeader("referer");
        this.clientsService.deleteUser(client);
        return "redirect:" + referer;
    }
    @GetMapping("/findclient")
    public String submissionResult( @RequestParam String searchField, Model model, @RequestParam(value="page",required = false,defaultValue = "0") int actualPage,
                                    @RequestParam (value = "pagesize", required = false,defaultValue="50")int pageSize) {
        System.out.println(searchField.trim());
        Page<Clients> clientsPage = this.clientsRepo.findByAnyField("%"+searchField.trim()+"%",PageRequest.of(actualPage,pageSize));
        System.out.println(clientsPage.getContent().size());
        model.addAttribute("clientsList",clientsPage.getContent());
        model.addAttribute("totalPages", new int[clientsPage.getTotalPages()]);
        model.addAttribute("actualPage", actualPage);
        model.addAttribute("clientsPage", clientsPage);
        return "showclients";
    }


    @Transactional
    @PostMapping ("/changestate/{id}")
    public String changestate(@PathVariable int id , HttpServletRequest request) throws MikrotikApiException {
        Clients client = this.clientsService.findClientById(id);
        String referer = request.getHeader("referer");
        this.clientsService.changeState(client);
        return "redirect:" + referer;
    }


}
