package com.gestion.mikrotik.services;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.respositories.IpAdresssRepository;
import com.gestion.mikrotik.respositories.VlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpAddressService {

    @Autowired
    public IpAdresssRepository ipRepo ;

    @Autowired
    VlanRepository vlanRepo;

    public IpAddressService(IpAdresssRepository ipRepo) {
        this.ipRepo = ipRepo;
    }
    public List<IpAddress> getAllip(){
        return this.ipRepo.findAll();
    }///Lista todas las IP


    public IpAdresssRepository getIpRepo() {
        return ipRepo;
    }

    public void setIpRepo(IpAdresssRepository ipRepo) {
        this.ipRepo = ipRepo;
    }

    public void  addAddress(IpAddress newIP){
        this.ipRepo.save(newIP);

    }

    public List<IpAddress> getAllIpByVlan(int id) {
        Vlan vlan = vlanRepo.findById(id).get();
       return  this.ipRepo.findIpaddressByVlan(vlan);
    }


    public boolean findIpAddress (String ipAddress){
        return this.ipRepo.findIpaddressByipAddress(ipAddress)!=null;
    }

    public List<IpAddress> findByIpAdresss (String ipAddress ){
        return this.ipRepo.findIpaddressByipAddress(ipAddress);
    }

}
