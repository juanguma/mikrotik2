package com.gestion.mikrotik.services;


import com.gestion.mikrotik.entities.Clients;
import com.gestion.mikrotik.respositories.ClientsRepository;
import com.gestion.mikrotik.respositories.IpAdresssRepository;
import lombok.SneakyThrows;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClientsService {

    @Autowired
    public ClientsRepository clientsRepo ;



    public static List<Map<String, String>> findClientswir(String ip)  {

            ApiConnection con = null;
            try{
                con = ApiConnection.connect(ip);
                con.login("telnet", "Camaleon21*");
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


}
