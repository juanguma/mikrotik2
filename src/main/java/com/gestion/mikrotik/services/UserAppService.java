package com.gestion.mikrotik.services;

import com.gestion.mikrotik.entities.UserApp;
import com.gestion.mikrotik.respositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserAppService {

    @Autowired
    UserAppRepository userAppRepository;

    public List<UserApp> getAppUsers(){
        return this.userAppRepository.findAll();

    }

    public UserApp createUser(UserApp user){
        return this.userAppRepository.save(user);
    }

}
