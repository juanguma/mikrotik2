package com.gestion.mikrotik.services;

import com.gestion.mikrotik.entities.UserRole;
import com.gestion.mikrotik.respositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;


    public List<UserRole> getRoles (){
        return this.userRoleRepository.findAll();
    }
}
