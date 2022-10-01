package com.gestion.mikrotik.respositories;

import com.gestion.mikrotik.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Integer> {
    @Query("select u from UserApp u where u.id = ?1")
    public abstract UserApp findByid(int id);



}
