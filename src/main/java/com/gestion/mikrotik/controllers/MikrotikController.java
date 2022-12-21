package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.MikrotikService;
import com.gestion.mikrotik.services.VlanService;
import lombok.SneakyThrows;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MikrotikController {
    @Autowired
    MikrotikService mikrotikService;
    @Autowired
    VlanService vlanService;
    @Autowired
    IpAddressService ipAddressService;

    MikrotikService service;

    public MikrotikController(MikrotikService service) {
        this.service = service;
    }

    @GetMapping("/mikrotik")
    public List<Mikrotik> mostrarMikrotik() {
        return service.getMikrotik();
    }

    @PostMapping("/mikrotik")
    public Mikrotik createMikrotik(@RequestBody Mikrotik mikrotikNuevo) {
        return this.service.saveMikrotik(mikrotikNuevo);
    }
    @SneakyThrows
    @GetMapping ("/showclient")
    public List  conectarMikrotik(){
        ApiConnection con = ApiConnection.connect("10.13.1.109");
        con.login("telnet","Camaleon21*");
        List<Map<String, String>> rs = con.execute("/system/routerboard/print");
        return  rs;

    }

    @GetMapping("/netuser/showrouter/")
     public String addMikrotik (Model model)

    {
        List<Vlan> vlanList = this.vlanService.getAllVlan();
        model.addAttribute("vlanList",vlanList);
        return "showrouter";

    }

    @GetMapping("/netuser/findrouters/{id}")
    public String findRouters(@PathVariable int id,Model model)  {
        List<Vlan> vlanList = this.vlanService.getAllVlan();
        model.addAttribute("vlanList",vlanList);
        List<IpAddress> ipList = this.ipAddressService.getAllIpByVlan(id);
        for (IpAddress ip:ipList)
        {
            if (this.mikrotikService.findMikrotik(ip)){
                System.out.println("ya existe  "+ip.ipAddress);

            }else{
                ApiConnection con = null;
                try {
                    con = ApiConnection.connect(ip.ipAddress);
                    con.login("telnet", "Camaleon21*");
                    List<Map<String, String>> routerInfo = con.execute("/system/routerboard/print");
                    String name = con.execute("/system/identity/print").get(0).get("name");
                    String reference = routerInfo.get(0).get("model");
                    String serial = routerInfo.get(0).get("serial-number");
                    boolean accesspoint = false;

                    if ((con.execute("/interface/wireless/print").size() != 0)) {
                        if (con.execute("/interface/wireless/print").get(0).get("mode").equals("bridge") || con.execute("/interface/wireless/print").get(0).get("mode").equals("ap-bridge")) {
                            accesspoint = true;
                            System.out.println("la direccion " + ip.ipAddress + " es ap ");
                        }
                    }
                    boolean configscript = false;
                    System.out.println(con.execute("/system/script/print").size());

                    if ((con.execute("/system/script/print").size() != 0)) {

                        for (int i = 0; i < (con.execute("/system/script/print").size()); i++) {
                            if (con.execute("/system/script/print").get(i).get("name").equals("Config")) {
                                configscript = true;
                                System.out.println("Config ook en  " + ip.ipAddress);
                            }

                        }

                    }
                    String mac = con.execute("/interface/print").get(0).get("mac-address");

                    String ssid = null;
                    if (con.execute("/interface/wireless/print").size() > 0) {

                        ssid = con.execute("/interface/wireless/print").get(0).get("ssid");
                        System.out.println(ssid);
                    }
                    System.out.println(mac);
                    Mikrotik newMikrotik = new Mikrotik(name, null, accesspoint, configscript, ip, serial, mac, ssid, "Camaleon");//llamo el constructor
                    this.mikrotikService.saveMikrotik(newMikrotik);


                    System.out.println("direccion ip " + ip.ipAddress + " Insertada");

                } catch (MikrotikApiException e) {
                    System.out.println(e.getMessage()+" "+ip.ipAddress);
                }
            }





        }
        return "showrouter";
    }


}

