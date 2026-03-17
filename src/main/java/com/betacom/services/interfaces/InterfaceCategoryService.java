package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.cart.CartRequest;
import com.betacom.dto.request.category.CategoryRequest;
import com.betacom.dto.response.cart.CartDTO;
import com.betacom.dto.response.category.CategoryDTO;

public interface InterfaceCategoryService {
	CategoryDTO getById(Long id) throws Exception;
	
	List<CategoryDTO> list() throws Exception;
	
	void create(CategoryRequest request) throws Exception;
	
	void update(CategoryRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
