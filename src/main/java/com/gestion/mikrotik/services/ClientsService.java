package com.gestion.mikrotik.services;


import com.gestion.mikrotik.entities.Clients;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.respositories.ClientsRepository;
import com.gestion.mikrotik.respositories.MikrotikRepository;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class ClientsService {

    @Autowired
    public ClientsRepository clientsRepo ;
    @Autowired
    public MikrotikRepository mikroRepo;



    public static List<Map<String, String>> findClientswir(String ip)  {

            ApiConnection con = null;
            try{
                con = ApiConnection.connect(ip);
                con.login("telnet", "Cronos2023*");
                System.out.println(con.isConnected());
                return  con.execute("/interface/wireless/access-list/print");

            }catch (MikrotikApiException e) {
                System.out.println("no es posible conectarse ");
                System.out.println(e.getMessage());
                return null;
            }
    }

    public void saveClientDB(Clients client){
        this.clientsRepo.save(client);
    }

    public List<Clients> listAllClients(){
        return this.clientsRepo.findAll();
    }


    public List<Map<String, String>> findClientsppp(Mikrotik mikrotik){
        ApiConnection con = null;
        try{
            con = ApiConnection.connect(mikrotik.getIpAddresses().getIpAddress());
            con.login("telnet", "Cronos2023*");
            System.out.println(con.isConnected());
            return  con.execute("/ppp/secret/print");

        }catch (MikrotikApiException e) {
            System.out.println("no es posible conectarse ");
            System.out.println(e.getMessage());
            return null;
        }

    }

    public List<Map<String, String>> findClientsdhcp(Mikrotik mikrotik){
        ApiConnection con = null;
        try{
            con = ApiConnection.connect(mikrotik.getIpAddresses().getIpAddress());
            con.login("telnet", "Cronos2023*");
            System.out.println(con.isConnected());
            return  con.execute("/ip/dhcp-server/lease/print");

        }catch (MikrotikApiException e) {
            System.out.println("no es posible conectarse ");
            System.out.println(e.getMessage());
            return null;
        }

    }

    public Clients findClientById(int id ){return this.clientsRepo.findClientsById(id);}



    public void deleteUser(Clients client) throws MikrotikApiException {

        String mikrotikIP = client.getNode().getIpAddresses().getIpAddress();
        Mikrotik mikrotik= this.mikroRepo.findMikrotikByipAddresses(client.getNode().getIpAddresses());
        ApiConnection con = null;
        try{
            con = ApiConnection.connect(mikrotik.getIpAddresses().getIpAddress());
            con.login("telnet", "Cronos2023*");
            System.out.println(con.isConnected());

        }catch (MikrotikApiException e) {
            System.out.println("no es posible conectarse ");
            System.out.println(e.getMessage());

        }
        List<Map<String, String>> result = con.execute("/interface/wireless/access-list/print where mac-address=" + client.getMacAdresss());
        if (!result.isEmpty()){

            System.out.println(result.get(0).get(".id"));
            //System.out.println(result.toString());
            try {
                con.execute("/interface/wireless/access-list/remove  .id=" +result.get(0).get(".id"));

            } catch (MikrotikApiException e) {
                throw new RuntimeException(e);
            }
            this.clientsRepo.deleteById(client.getId());

        }

    }


    public void changeState(Clients client) throws MikrotikApiException {

        String mikrotikIP = client.getNode().getIpAddresses().getIpAddress();
        Mikrotik mikrotik= this.mikroRepo.findMikrotikByipAddresses(client.getNode().getIpAddresses());
        ApiConnection con = null;
        try{
            con = ApiConnection.connect(mikrotik.getIpAddresses().getIpAddress());
            con.login("telnet", "Cronos2023*");
            System.out.println(con.isConnected());

        }catch (MikrotikApiException e) {
            System.out.println("no es posible conectarse ");
            System.out.println(e.getMessage());

        }
        System.out.println(client.getClienttype());
        if (client.getClienttype().equals("wireless")){
            System.out.println("wireless");
            List<Map<String, String>> result = con.execute("/interface/wireless/access-list/print where mac-address=" + client.getMacAdresss());
            if (!result.isEmpty()){

                System.out.println(result.get(0).get(".id"));
                //System.out.println(result.toString());
                try {
                    // con.execute("/interface/wireless/access-list/print  .id=" +result.get(0).get(".id"));
                    //con.execute("/interface/gre/set remote-address=172.16.1.1 .id=gre1");
                    String state = result.get(0).get("disabled");
                    System.out.println(state);
                    if (state.equals("false")){
                        con.execute("/interface/wireless/access-list/set disabled=true .id="+result.get(0).get(".id"));
                        this.clientsRepo.updateDisabledClientById(true,client.getId());

                    } else if (state.equals("true")) {
                        con.execute("/interface/wireless/access-list/set disabled=false .id="+result.get(0).get(".id"));
                        this.clientsRepo.updateDisabledClientById(false,client.getId());
                    }

                    //System.out.println(a.get(0).toString());


                } catch (MikrotikApiException e) {
                    throw new RuntimeException(e);
                }

            } else {
                System.out.println("vacio");
            }


        } else if (client.getClienttype().equals("cable")) {


            List<Map<String, String>> result = con.execute("/ip/firewall/address-list/print where  list=suspendidos and address=" + client.getIpadd());
            String state = result.get(0).get("disabled");
            System.out.println(result.get(0).toString());
            if (state.equals("false")){
                System.out.println("Activo");
                con.execute("/ip/firewall/address-list/set disabled=true .id="+result.get(0).get(".id"));
                System.out.println(result.get(0).toString());
                this.clientsRepo.updateDisabledClientById(true,client.getId());

            } else if (state.equals("true")) {
                System.out.println("suspendido");
                System.out.println(result.get(0).toString());
                con.execute("/ip/firewall/address-list/set disabled=false .id="+result.get(0).get(".id"));
                this.clientsRepo.updateDisabledClientById(false,client.getId());
            }




            
        }


    }

    public boolean isActive(Clients client) throws MikrotikApiException {
        Mikrotik mikrotik = this.mikroRepo.findMikrotikByipAddresses(client.getNode().getIpAddresses());

        ApiConnection con = ApiConnection.connect(mikrotik.getIpAddresses().getIpAddress());
        con.login("telnet", "Cronos2023*");
        System.out.println(con.isConnected());

        if (client.getClienttype().equals("wireless")) {

            System.out.println("wireless");
            List<Map<String, String>> results = con.execute("/interface/wireless/registration-table/print");

            if (results.toString().contains(client.getMacAdresss())) {
                System.out.println("conectado");
                return true;
            }else{
                System.out.println("noConectado");
                return false;
            }

           /** for (Map<String, String> result : results) {
                System.out.println(result.get("comment"));

                if (result.containsValue(client.getMacAdresss())) {
                    System.out.println("conectado");
                    return true;
                }else{
                    System.out.println("noConectado");
                    return false;
                }


            }**/


        } else if (client.getClienttype().equals("cable")) {
            System.out.println("cable");

            return false;
        }

        return false;

    }

}
