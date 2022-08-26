package com.gestion.mikrotik.services;

import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.respositories.MikrotikRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MikrotikService {
    public MikrotikRepository repository;

    public MikrotikService(MikrotikRepository repository) {
        this.repository = repository;
    }

 public List<Mikrotik> getMikrotik(){
        return this.repository.findAll();
    }


    public Mikrotik guardarMikrotik(Mikrotik newMikrotik){
        return this.repository.save(newMikrotik);
    }

}
