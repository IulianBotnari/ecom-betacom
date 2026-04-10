package com.betacom.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betacom.enums.Roles;
import com.betacom.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
	

	List<User> searchUsers(
	    @Param("id") Long id, 
	    @Param("name") String name, 
	    @Param("lastName") String lastName, 
	    @Param("email") String email, 
	    @Param("codiceFiscale") String codiceFiscale,
	    @Param("role") Roles role,
	    @Param("createDate") LocalDate createDate
	);
}
