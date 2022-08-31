package com.gestion.mikrotik.entities;


import javax.persistence.*;
import java.util.Stack;


@Entity
@Table(name = "client_routers")
public class Mikrotik {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;






}
