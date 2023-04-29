package com.gestion.mikrotik.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "network_role")
public class NetworkRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String netRoleName;




}
