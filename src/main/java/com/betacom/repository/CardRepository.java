package com.betacom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{

}
