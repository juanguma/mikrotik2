package com.gestion.mikrotik.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity

public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
     private String nombre;
     private String correo;
     private String  passwd;
     @ManyToOne
     @JoinColumn(name = "rol_nombre")
     private  UserRole rol;

    public UserRole getRol() {
        return rol;
    }

    public void setRol(UserRole rol) {
        this.rol = rol;
    }


    public UserApp(String nombre, String correo, String passwd, UserRole rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.passwd = passwd;
        this.rol = rol;
    }
}
