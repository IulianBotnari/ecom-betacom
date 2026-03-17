package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.cart.CartRequest;
import com.betacom.dto.request.cart_item.CartItemRequest;
import com.betacom.dto.response.cart.CartDTO;
import com.betacom.dto.response.cart_item.CartItemDTO;

public interface InterfaceCartService {
	CartDTO getById(Long id) throws Exception;
	
	List<CartDTO> list() throws Exception;
	
	void create(CartRequest request) throws Exception;
	
	void update(CartRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
