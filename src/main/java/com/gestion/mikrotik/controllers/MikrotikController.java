package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.respositories.MikrotikRepository;
import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.MikrotikService;
import com.gestion.mikrotik.services.VlanService;
import com.jcraft.jsch.JSchException;
import lombok.SneakyThrows;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.gestion.mikrotik.utilidades.pruebas.*;

@Controller
public class MikrotikController {
    @Autowired
    MikrotikService mikrotikService;
    @Autowired
    VlanService vlanService;
    @Autowired
    IpAddressService ipAddressService;

    MikrotikService service;
    private final MikrotikRepository mikrotikRepository;

    public MikrotikController(MikrotikService service,
                              MikrotikRepository mikrotikRepository) {
        this.service = service;
        this.mikrotikRepository = mikrotikRepository;
    }
    public abstract class MikrotikConnector {

        protected ApiConnection connection;

        protected void connect(String host, String username, String password) throws Exception {
            connection = ApiConnection.connect(host);
            connection.login(username, password);
        }

        protected void disconnect() throws Exception {
            connection.close();
        }

        // Método abstracto que debe ser implementado por las clases concretas
        protected abstract void executeOperations();
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

    @GetMapping("/netuser/findrouters/")
    public String findAallRouters(Model model)  {

        List<IpAddress> ipList = this.ipAddressService.getAllip();
        for (IpAddress ip:ipList)
        {
            if (this.mikrotikService.findMikrotik(ip)){
                System.out.println("ya existe  "+ip.ipAddress);

            }else{
                ApiConnection con = null;
                try {
                    con = ApiConnection.connect(ip.ipAddress);
                        con.login("telnet", "Cronos2023*");
                    List<Map<String, String>> routerInfo = con.execute("/system/routerboard/print");
                    String name = con.execute("/system/identity/print").get(0).get("name");
                    String reference = routerInfo.get(0).get("model");
                    String serial = routerInfo.get(0).get("serial-number");
                    boolean accesspoint = false;

                    if ((con.execute("/interface/wireless/print").size() != 0)) {
                        if (con.execute("/interface/wireless/print").get(0).get("mode").equals("bridge") || con.execute("/interface/wireless/print").get(0).get("mode").equals("ap-bridge")) {
                            accesspoint = true;
                            //System.out.println("la direccion " + ip.ipAddress + " es ap ");
                        }
                    }
                    boolean configscript = false;
                    System.out.println(con.execute("/system/script/print").size());

                    if ((con.execute("/system/script/print").size() != 0)) {

                        for (int i = 0; i < (con.execute("/system/script/print").size()); i++) {
                            if (con.execute("/system/script/print").get(i).get("name").equals("Config")) {
                                configscript = true;
                                //System.out.println("Config ook en  " + ip.ipAddress);
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
                    Mikrotik newMikrotik = new Mikrotik(name, null, accesspoint, configscript, ip, serial, mac, ssid, "cronos");//llamo el constructor
                    this.mikrotikService.saveMikrotik(newMikrotik);


                    System.out.println("direccion ip " + ip.ipAddress + " Insertada");

                } catch (MikrotikApiException e) {
                    System.out.println(e.getMessage()+" "+ip.ipAddress);
                }
            }

        }
        return "showrouter";
    }
    @GetMapping("/netuser/nobackupmikrotik")
    public String   findNoBackupMikrotik(){
        List <Mikrotik> mikrotikList = this.mikrotikService.findMikrotikNoConfig();
        System.out.println(mikrotikList.size());
        for (Mikrotik p: mikrotikList){
            //recorro el for paraconectarme
            System.out.println(p.getName()+" "+p.getIpAddresses().getIpAddress());
            execRemoteSsh(p.getIpAddresses().getIpAddress(),"telnet","Camaleon21*","/tool fetch url=http://192.168.99.21/updatetelnet.txt mode=http dst-path=Update.rsc \n");
            execRemoteSsh(p.getIpAddresses().getIpAddress(),"telnet","Camaleon21*","/import file-name=Update.rsc \n");
            execRemoteSsh(p.getIpAddresses().getIpAddress(),"telnet","Camaleon21*","/file remove Update.rsc \n");
            //System.out.println(p.getName()+"-"+p.getIpAddresses().getIpAddress());

        }
        return "falta return";
    }

    @GetMapping("/netuser/updatedb")
    public String updateDB(){
        //List<IpAddress> ipList = this.ipAddressService.getAllip();
        List<IpAddress> ipList = this.ipAddressService.findByIpAdresss("192.168.32.7");
        for (IpAddress ip1:ipList){
            System.out.println(ip1.getIpAddress());
            //IpAddress ip1= this.service.findIpAdress("10.0.0.2");
            Mikrotik mikro1= this.mikrotikService.findMikrotikByIp(ip1.getIpAddress());
            try{
                Mikrotik mikro2 = createObjMikrotik(ip1);
                System.out.println(mikro2.toString());

                if(mikro1!=null){
                    if(mikro1.getSerial().equals(mikro2.getSerial())){

                        //System.out.println("SerialesIguales");
                        mikro1.setName(mikro2.getName());
                        //System.out.println("la configscrpt es " +mikro2.isConfigscript());
                        mikro1.setAccesspoint(mikro2.isAccesspoint());
                        mikro1.setConfigscript(mikro2.isConfigscript());
                        mikro1.setSsid(mikro2.getSsid());
                        this.mikrotikService.saveMikrotik(mikro1);

                    }
                    else{
                        this.mikrotikService.saveMikrotik(mikro2);

                    }

                }


            } catch (Exception e) {
                System.out.printf(e.getMessage());
            }

        }



        return "response";
    }


    @GetMapping("/netuser/mikrotikbyvlan")
    public String mikrotikbyvlan(){
        Vlan vlan = this.vlanService.getVlanById(19);
        System.out.println(vlan.getVlanName());


        return "ok";


    }

    @GetMapping("/netuser/showrouter/{id}")
    public String showRouterByVlan(Model model, @PathVariable int id){
        List<Mikrotik> mikrotikList= this.mikrotikService.findMikrotikByVlan(this.vlanService.getVlanById(id));
        model.addAttribute(mikrotikList);
        return "/showrouter";
    }

    @GetMapping ("/netuser/changepass/")
    public String changePassword(Model model) throws MikrotikApiException, JSchException, IOException {

        List<IpAddress> ipAddressList= this.ipAddressService.getAllip();
        for ( IpAddress ip:ipAddressList){

        changePass(ip.getIpAddress());

        }
        return "ok";
    }

    @GetMapping ("/prueba")
    public  String prueba(){
        class prueba extends  MikrotikConnector{

            @Override
            protected void executeOperations() {
                try {
                    connect("10.0.1.18", "telnet", "Cronos2023*");


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("###################################################################################################" +
                            "" +
                            "");
                } finally {
                    try {
                        disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }

    }
        return "ok";
}}

