package com.betacom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.model.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long>{

}
