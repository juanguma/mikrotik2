package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.UserApp;
import com.gestion.mikrotik.entities.UserRole;
import com.gestion.mikrotik.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Restcontroller {
    @Autowired
    UserRoleService userRoleService;
    @GetMapping("/addusers1")
    public List<UserRole> addusers (Model model){
        return this.userRoleService.getRoles();
    }
}
