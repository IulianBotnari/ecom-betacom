package com.betacom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.model.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long>{
	
	Size findBySize(String size) throws Exception;

}
