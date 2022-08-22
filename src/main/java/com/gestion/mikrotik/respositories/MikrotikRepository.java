package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.Mikrotik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MikrotikRepository extends JpaRepository<Mikrotik, Long> {
}
