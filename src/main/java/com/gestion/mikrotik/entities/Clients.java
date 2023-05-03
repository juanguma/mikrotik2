package com.gestion.mikrotik.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "clients")
public class Clients {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    int id;
  private   String name;
  private  String address;
  private  String identification;
  private  String telephone;
  private  String telephone2;
  private  String plan;
    @ManyToOne
    @JoinColumn(name = "node_id")
  private    Mikrotik  node;
  private   Date installationDate;
  private   String clientStatus;
  private   boolean scriptBackup;
  private   boolean disabledClient;
  private   Date createdIn;
  private   Date modificatedAt;
  private   String macAdresss;
  private   String ipadd;
  private   String clienttype;

    public Mikrotik getNode() {
        return node;
    }

    public void setNode(Mikrotik node) {
        this.node = node;
    }




}

