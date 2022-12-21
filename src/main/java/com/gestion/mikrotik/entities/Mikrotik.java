package com.gestion.mikrotik.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Stack;

@Getter
@Setter
@Entity

@Table(name = "client_routers")
public class Mikrotik {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String  name;
    private String rol;
    private boolean accesspoint;
    private boolean configscript;
    @ManyToOne
    @JoinColumn(name = "ip_addresses_id")
    private IpAddress ipAddresses;
    private String serial;
    private String mac;
    private String ssid;
    private String description;

    public IpAddress getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(IpAddress ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    public Mikrotik() {
    }

    public Mikrotik(String name, String rol, boolean accesspoint, boolean configscript, IpAddress ipAddresses, String serial, String mac, String ssid, String description) {
        this.name = name;
        this.rol = rol;
        this.accesspoint = accesspoint;
        this.configscript = configscript;
        this.ipAddresses = ipAddresses;
        this.serial = serial;
        this.mac = mac;
        this.ssid = ssid;
        this.description = description;
    }
}
