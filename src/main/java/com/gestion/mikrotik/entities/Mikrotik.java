package com.gestion.mikrotik.entities;


import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Stack;


@Entity

@Table(name = "client_routers")
public class Mikrotik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public IpAddress getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(IpAddress ipAddresses) {
        this.ipAddresses = ipAddresses;
    }


}
