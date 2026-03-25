package com.betacom.services.interfaces;

import java.util.List;
import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.request.product.ProudctUpdate;
import com.betacom.dto.response.product.ProductsDTO;
import com.betacom.enums.Genders;
import com.betacom.model.Product;

public interface InterfaceProductService {
	ProductsDTO getById(Long id) throws Exception;
	
	List<ProductsDTO> list() throws Exception;
	
	void create(ProductRequest request) throws Exception;
	
	void update(ProudctUpdate request) throws Exception;
	
	void delete(Long id) throws Exception;
	
	Product getProductModelById(Long id) throws Exception;

	List<? extends ProductsDTO> multiFilter(Long id, String name, String modello, Long categoryId, Genders gender,
			String material, Double price) throws Exception;
}
