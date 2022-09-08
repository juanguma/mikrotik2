package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Vlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VlanRepository  extends JpaRepository<Vlan,Integer> {


}
