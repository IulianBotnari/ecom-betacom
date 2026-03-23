package com.betacom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.model.Cart;
import com.betacom.model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	

}
