package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.card.CardRequest;
import com.betacom.dto.request.cart_item.CartItemRequest;
import com.betacom.dto.response.card.CardDTO;
import com.betacom.dto.response.cart_item.CartItemDTO;

public interface InterfaceCartItemService {
	CartItemDTO getById(Long id) throws Exception;
	
	List<CartItemDTO> list() throws Exception;
	
	void create(CartItemRequest request) throws Exception;
	
	void update(CartItemRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
