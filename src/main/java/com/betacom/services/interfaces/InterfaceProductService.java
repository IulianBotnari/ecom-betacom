package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.payment_method.PaymentMethodRequest;
import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.response.payment_method.PaymentMethodDTO;
import com.betacom.dto.response.product.ProductsDTO;

public interface InterfaceProductService {
	ProductsDTO getById(Long id) throws Exception;
	
	List<ProductsDTO> list() throws Exception;
	
	void create(ProductRequest request) throws Exception;
	
	void update(ProductRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
