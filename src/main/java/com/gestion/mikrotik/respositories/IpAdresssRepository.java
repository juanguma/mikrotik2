package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpAdresssRepository extends JpaRepository <IpAddress, Long>{
}
