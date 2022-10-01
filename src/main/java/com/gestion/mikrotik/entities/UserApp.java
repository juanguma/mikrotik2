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
     private String name;
     private String email;
     private String  passwd;

     private boolean status;
     @ManyToOne
     @JoinColumn(name = "rol_name")
     private  UserRole rol;

    public UserRole getRol() {
        return rol;
    }

    public void setRol(UserRole rol) {
        this.rol = rol;
    }


    public UserApp(String name, String correo, String passwd, UserRole rol) {
        this.name = name;
        this.email = correo;
        this.passwd = passwd;
        this.rol = rol;

    }
}
