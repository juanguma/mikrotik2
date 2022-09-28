package com.gestion.mikrotik.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UserRole {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Id
    String nombre;




}
