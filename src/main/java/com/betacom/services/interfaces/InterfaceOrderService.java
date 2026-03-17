package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.order.OrderRequest;
import com.betacom.dto.response.order.OrderDTO;
import com.betacom.dto.response.order_details.OrderDetailsDTO;

public interface InterfaceOrderService {
	OrderDTO getById(Long id) throws Exception;
	
	List<OrderDTO> list() throws Exception;
	
	void create(OrderRequest request) throws Exception;
	
	void update(OrderRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
