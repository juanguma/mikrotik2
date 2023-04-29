package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository <Clients, Long>  {


}
