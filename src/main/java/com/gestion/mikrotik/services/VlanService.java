package com.gestion.mikrotik.services;

import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.respositories.VlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class VlanService {
    VlanRepository vlanRepo ;

    public VlanService(VlanRepository vlanRepo) {
        this.vlanRepo = vlanRepo;
    }
    public Vlan addVlan (Vlan newVlan) {

        return this.vlanRepo.save(newVlan);
    }

    public List<Vlan> getAllVlan(){
        return this.vlanRepo.findAll();
    }
}
