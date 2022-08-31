package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.Vlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VlanRepository  extends JpaRepository<Vlan,Integer> {

}
