package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.category.CategoryRequest;
import com.betacom.dto.request.order.OrderRequest;
import com.betacom.dto.request.order_details.OrderDetailRequest;
import com.betacom.dto.response.category.CategoryDTO;
import com.betacom.dto.response.order_details.OrderDetailsDTO;

public interface InterfaceOrderDetailService {
	OrderDetailsDTO getById(Long id) throws Exception;
	
	List<OrderDetailsDTO> list() throws Exception;
	
	void create(OrderDetailRequest request) throws Exception;
	
	void update(OrderDetailRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
