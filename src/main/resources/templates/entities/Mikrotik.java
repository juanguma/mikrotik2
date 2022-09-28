package com.gestion.mikrotik.entities;


import javax.persistence.*;


@Entity
@Table(name = "client_routers")
public class Mikrotik {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String rol;

}
