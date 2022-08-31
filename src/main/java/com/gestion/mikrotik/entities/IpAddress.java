package com.gestion.mikrotik.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class IpAddress {
    @Id
   public  Long id;
    @ManyToOne
    @JoinColumn(name = "vlan_id_id")
  public   Vlan vlanId;
   public   int numericIP;

    public IpAddress(Long id, Vlan vlanId, int numericIP) {
        this.id = id;
        this.vlanId = vlanId;
        this.numericIP = numericIP;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumericIP() {
        return numericIP;
    }

    public void setNumericIP(int numericIP) {
        this.numericIP = numericIP;
    }

    public Vlan getVlanId() {
        return vlanId;
    }

    public IpAddress() {
    }

    public void setVlanId(Vlan vlanId) {
        this.vlanId = vlanId;
    }
}
