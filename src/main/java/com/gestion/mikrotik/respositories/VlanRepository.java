package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.UserApp;
import com.gestion.mikrotik.entities.Vlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VlanRepository  extends JpaRepository<Vlan,Integer> {

    @Query("select v from Vlan v where v.vlanRef = ?1")
    public abstract Vlan findByVlanId(String vlanId);


}
