package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.services.MikrotikService;
import lombok.SneakyThrows;
import me.legrange.mikrotik.ApiConnection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MikrotikController {

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
        return this.service.guardarMikrotik(mikrotikNuevo);
    }
    @SneakyThrows
    @GetMapping ("/showclient")
    public List  conectarMikrotik(){
        ApiConnection con = ApiConnection.connect("10.13.1.109");
        con.login("telnet","Camaleon21*");
        List<Map<String, String>> rs = con.execute("/queue/simple/print");
        return  rs;

    }
}

