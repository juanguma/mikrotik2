package com.gestion.mikrotik.services;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.respositories.IpAdresssRepository;
import com.gestion.mikrotik.respositories.MikrotikRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MikrotikService {
    public MikrotikRepository repository;
    private final IpAdresssRepository ipAdresssRepository;

    public MikrotikService(MikrotikRepository repository,
                           IpAdresssRepository ipAdresssRepository) {
        this.repository = repository;
        this.ipAdresssRepository = ipAdresssRepository;
    }

    public List<Mikrotik> getMikrotik(){
        return this.repository.findAll();
    }


    public Mikrotik saveMikrotik(Mikrotik newMikrotik){
        return this.repository.save(newMikrotik);
    }

    public IpAddress findIpAdress (String ipAddress){
        return this.ipAdresssRepository.findIpaddressByipAddress2(ipAddress);
    }

  public List <Mikrotik>  findMikrotikNoConfig (){return  this.repository.findMikrotikByconfigscript();}

    public boolean  findMikrotik (IpAddress ipAddress){
        if (this.repository.findMikrotikByipAddresses(ipAddress)!=null){
            return  true;
        }
        return false;

    }

    public Mikrotik findMikrotikByIp(String ip){
        IpAddress ipAddress = this.ipAdresssRepository.findIpaddressByipAddress2(ip);
        return this.repository.findMikrotikByipAddresses(ipAddress);
    }

    public List<Mikrotik> findMikrotikAP (){
        return  this.repository.findMikrotikByaccesspoint();
    }

    public List<Mikrotik> findMikrotikByVlan(Vlan vlan){
        return this.repository.findMikrotikByIpAddressesVlan(vlan);
    }



}
