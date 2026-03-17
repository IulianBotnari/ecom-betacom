package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.order_details.OrderDetailRequest;
import com.betacom.dto.response.order_details.OrderDetailsDTO;
import com.betacom.services.interfaces.InterfaceOrderDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderDetailServiceImpl implements InterfaceOrderDetailService{@Override
	public OrderDetailsDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDetailsDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(OrderDetailRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(OrderDetailRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
