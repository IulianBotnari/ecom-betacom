package com.betacom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.model.Order;
import com.betacom.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	Optional <Order> findByUser(User user)throws Exception;

}
