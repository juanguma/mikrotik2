package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Vlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IpAdresssRepository extends JpaRepository <IpAddress, Long>{

    public  abstract List<IpAddress> findIpaddressByVlan(Vlan vlan);


    public  abstract List<IpAddress> findIpaddressByipAddress(String ipAddress);


}
