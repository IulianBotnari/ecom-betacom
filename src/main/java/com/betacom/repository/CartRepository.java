package com.betacom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.dto.response.cart.CartDTO;
import com.betacom.model.Cart;
import com.betacom.model.CartItem;
import com.betacom.model.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	Optional<Cart> findByUser(User user);
}


