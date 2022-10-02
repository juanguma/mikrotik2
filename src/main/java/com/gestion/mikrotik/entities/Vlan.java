package com.gestion.mikrotik.entities;

import lombok.*;

import javax.persistence.*;
import java.net.InetAddress;

@Getter

@NoArgsConstructor

@Entity
@Table(name = "vlans")

public class Vlan {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;
    String vlanName;
    @Column(name="vlanId", unique= true)
    String vlanRef;
    String networkAddress;
    int  networkHashcode;

    @SneakyThrows
    public Vlan(String vlanRef, String vlanName, String networkAddress) {
        this.vlanRef = vlanRef;
        this.vlanName = vlanName;
        this.networkAddress = networkAddress;
        InetAddress inet = InetAddress.getByName(networkAddress);
        this.networkHashcode= inet.hashCode();
    }

    public void setVlanName(String vlanName) {
        this.vlanName = vlanName;
    }

    public void setVlanRef(String vlanRef) {
        this.vlanRef = vlanRef;
    }

    public void setNetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
    }

    public void setNetworkHashcode(int networkHashcode) {
        this.networkHashcode = networkHashcode;
    }
}
