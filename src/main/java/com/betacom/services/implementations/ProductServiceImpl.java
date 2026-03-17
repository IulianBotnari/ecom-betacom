package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.response.product.ProductsDTO;
import com.betacom.services.interfaces.InterfaceProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements InterfaceProductService{@Override
	public ProductsDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductsDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(ProductRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ProductRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
