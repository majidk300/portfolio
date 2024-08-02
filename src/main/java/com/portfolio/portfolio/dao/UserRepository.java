package com.portfolio.portfolio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portfolio.portfolio.Entities.adminEntity;

public interface UserRepository extends JpaRepository<adminEntity , Integer> {

    @Query("select u from adminEntity u where u.email=:email")

    public adminEntity getUserByUserName(@Param("email") String email);
    
}
