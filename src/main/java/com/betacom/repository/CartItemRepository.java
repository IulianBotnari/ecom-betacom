package com.betacom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.model.Cart;
import com.betacom.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	@Modifying
    @Transactional
    void deleteAllByCart(Cart cart);

}
