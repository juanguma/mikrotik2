package com.gestion.mikrotik.services;

import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.respositories.VlanRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;

@Service

public class VlanService {
    VlanRepository vlanRepo ;

    public VlanService(VlanRepository vlanRepo) {
        this.vlanRepo = vlanRepo;
    }
    @SneakyThrows
    public Vlan addVlan (Vlan newVlan) {
        InetAddress inet = InetAddress.getByName(newVlan.getNetworkAddress());
        newVlan.setNetworkHashcode((inet.hashCode()));
        return this.vlanRepo.save(newVlan);
    }

    public List<Vlan> getAllVlan(){
        return this.vlanRepo.findAll();
    }

    public Vlan getVlanById(Integer id ){
        return this.vlanRepo.findById(id).get();
    }

    @SneakyThrows
    public Vlan updateVlan(Vlan vlan, int id ){
        Vlan vlanActual= this.vlanRepo.findById(id).get();
        vlanActual.setVlanName(vlan.getVlanName());
        vlanActual.setNetworkAddress(vlan.getNetworkAddress());
        InetAddress inet = InetAddress.getByName(vlanActual.getNetworkAddress());
        vlanActual.setNetworkHashcode((inet.hashCode()));


        return this.vlanRepo.save(vlanActual);
    }
}
