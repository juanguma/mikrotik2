package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.Clients;
import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.entities.Vlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository <Clients, Long>  {
    @Query("select c from Clients c where c.node.id = ?1")
    Page<Clients> findByNode_Id(Long id, Pageable pageable);

    @Query("select c from Clients c where c.id = ?1")
    Clients findClientsById(int id);

    @Query("""
            select c from Clients c
            where c.name like ?1 or c.telephone like ?1 or c.telephone2 like ?1 or c.identification like ?1 or c.address like ?1 or c.macAdresss like ?1 order by c.id""")
    Page<Clients> findByAnyField(String searchField,Pageable pageable);

    @Modifying
    @Query("update Clients c set c.disabledClient = ?1 where c.id = ?2")
    int updateDisabledClientById(boolean disabledClient, int id);

    @Query("select c from Clients c where c.node.ipAddresses.ipAddress = ?1")
    List<Clients> findByNode_IpAddresses_IpAddress(String ipAddress);






    void deleteById(int id);
}
