package com.betacom.services.implementations;

import java.util.List;
import java.util.Locale.Category;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.response.product.ProductsDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.dto_mappers.map_model.ModelMappers;
import com.betacom.model.Product;
import com.betacom.repository.ProductRepository;
import com.betacom.services.interfaces.InterfaceProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements InterfaceProductService{
	
	private final ProductRepository productR;
	private final CategoryServiceImpl categoryS;
	private final ModelMappers modelM;
	private final DtoResponseMapper dtoResponseMapper;
	
	@Override
	public ProductsDTO getById(Long id) throws Exception {
		
		Product product = productR.findById(id).orElseThrow(()-> new Exception("Prodotto non trovato"));
		
		return DtoResponseMapper.productsDTO(product);
	}

	@Override
	public List<ProductsDTO> list() throws Exception {
		List<Product> lista = productR.findAll();
		return lista.stream().map(el -> DtoResponseMapper.productsDTO(el)).collect(Collectors.toList());
	}

	@Override
	public void create(ProductRequest request) throws Exception {
		com.betacom.model.Category category = categoryS.getModelCategoryById(request.getCategoryId());
		Product product = modelM.product(request, category);
		
		productR.save(product);
		
		
	}

	@Override
	public void update(ProductRequest request) throws Exception {
		com.betacom.model.Category category = categoryS.getModelCategoryById(request.getCategoryId());
		Product product = modelM.product(request, category);
		
		productR.save(product);
		
	}

	@Override
	public void delete(Long id) throws Exception {
		Product product = productR.findById(id).orElseThrow(()-> new Exception("Prodotto non trovato"));
		
		productR.delete(product);
		
	}
	
	@Override
	public Product getProductModelById(Long id) throws Exception {
		
		Product product = productR.findById(id).orElseThrow(()-> new Exception("Prodotto non trovato"));
		
		return product;
	}

}
