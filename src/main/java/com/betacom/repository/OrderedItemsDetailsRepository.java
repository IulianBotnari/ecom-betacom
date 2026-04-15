package com.betacom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.model.OrderedItemsDetails;

@Repository
public interface OrderedItemsDetailsRepository extends JpaRepository<OrderedItemsDetails, Long>{

}
