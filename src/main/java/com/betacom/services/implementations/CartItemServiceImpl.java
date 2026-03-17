package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.cart_item.CartItemRequest;
import com.betacom.dto.response.cart_item.CartItemDTO;
import com.betacom.services.interfaces.InterfaceCartItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service

public class CartItemServiceImpl implements InterfaceCartItemService{@Override
	public CartItemDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CartItemDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(CartItemRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CartItemRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
