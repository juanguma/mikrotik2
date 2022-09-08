package com.gestion.mikrotik.services;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.respositories.IpAdresssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IpAdressService {

    @Autowired
    public IpAdresssRepository ipRepo ;
    @Autowired
    public  VlanService vlanService;

    public IpAdressService(IpAdresssRepository ipRepo) {
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
       return  this.ipRepo.findIpaddressByVlan(id);
    }

}
