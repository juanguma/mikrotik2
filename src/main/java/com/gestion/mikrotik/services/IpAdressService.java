package com.gestion.mikrotik.services;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.respositories.IpAdresssRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IpAdressService {
    public IpAdresssRepository ipRepo ;

    public IpAdressService(IpAdresssRepository ipRepo) {
        this.ipRepo = ipRepo;
    }
    public List<IpAddress> getAllip(){
        return this.ipRepo.findAll();
    }

    public IpAdresssRepository getIpRepo() {
        return ipRepo;
    }

    public void setIpRepo(IpAdresssRepository ipRepo) {
        this.ipRepo = ipRepo;
    }
}
