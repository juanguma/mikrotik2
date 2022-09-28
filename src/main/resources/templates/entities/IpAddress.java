package com.gestion.mikrotik.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter

public class IpAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public  Integer id;
    @ManyToOne
    @JoinColumn(name = "vlan_id_id")
     public   Vlan vlan;
    public String ipAddress;
   public   int numericIP;

    public IpAddress(Vlan vlanId, int numericIP,String ipAddress) {
        this.ipAddress=ipAddress;
        this.vlan = vlanId;
        this.numericIP = numericIP;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumericIP() {
        return numericIP;
    }

    public void setNumericIP(int numericIP) {
        this.numericIP = numericIP;
    }

    public Vlan getVlanId() {
        return vlan;
    }

    public IpAddress() {
    }

    public void setVlanId(Vlan vlanId) {
        this.vlan = vlan;
    }
}
