package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Mikrotik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MikrotikRepository extends JpaRepository<Mikrotik, Long> {


    @Query("select m from Mikrotik m where m.ipAddresses = ?1")
    public  abstract Mikrotik  findMikrotikByipAddresses(IpAddress ipAddress);


    @Query("select m from Mikrotik m where m.serial = ?1")
    public abstract boolean findMikrotikByserial(String serial);


    @Query("select m from Mikrotik m where m.configscript = 0")
    public abstract List<Mikrotik>  findMikrotikByconfigscript();


}
