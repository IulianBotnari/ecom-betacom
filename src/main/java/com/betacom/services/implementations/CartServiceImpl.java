package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.cart.CartRequest;
import com.betacom.dto.response.cart.CartDTO;
import com.betacom.services.interfaces.InterfaceCartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CartServiceImpl implements InterfaceCartService{@Override
	public CartDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CartDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(CartRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CartRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
