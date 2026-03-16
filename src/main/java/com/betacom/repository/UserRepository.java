package com.betacom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
