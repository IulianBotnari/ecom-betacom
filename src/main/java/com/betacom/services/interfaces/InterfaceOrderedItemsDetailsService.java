package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.category.CategoryRequest;
import com.betacom.dto.request.order.OrderRequest;
import com.betacom.dto.request.ordered_items_details.OrderedItemsDetailsRequest;
import com.betacom.dto.response.category.CategoryDTO;
import com.betacom.dto.response.ordered_items_details.OrderedItemsDetailsDTO;

public interface InterfaceOrderedItemsDetailsService {
	OrderedItemsDetailsDTO getById(Long id) throws Exception;
	
	List<OrderedItemsDetailsDTO> list() throws Exception;
	
	void create(OrderedItemsDetailsRequest request) throws Exception;
	
	void update(OrderedItemsDetailsRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
