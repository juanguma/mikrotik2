package com.gestion.mikrotik.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "vlans")

public class Vlan {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;
    String vlanName;
    String vlanId;
    String networkAddress;
    int networkHashcode;

    public Vlan(String vlanId, String vlanName, String networkAddress) {
        this.vlanId = vlanId;
        this.vlanName = vlanName;
        this.networkAddress = networkAddress;
    }
}
