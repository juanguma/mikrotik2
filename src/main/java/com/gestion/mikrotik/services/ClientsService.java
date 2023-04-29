package com.gestion.mikrotik.services;


import com.gestion.mikrotik.respositories.ClientsRepository;
import com.gestion.mikrotik.respositories.IpAdresssRepository;
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


    public static List<Map<String, String>> findClientswir(String ip) throws MikrotikApiException {
        ApiConnection con = null;
        con = ApiConnection.connect(ip);
        con.login("telnet", "Camaleon21*");
        return con.execute("/interface/wireless/access-list/print");


    }
}
