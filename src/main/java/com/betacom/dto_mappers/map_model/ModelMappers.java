package com.betacom.dto_mappers.map_model;
import org.springframework.stereotype.Component;

import com.betacom.dto.request.category.CategoryRequest;
import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.request.size.SizeRequest;
import com.betacom.enums.Genders;
import com.betacom.enums.Sizes;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.repository.CategoryRepository;
import com.betacom.services.implementations.CategoryServiceImpl;
import com.betacom.services.implementations.ProductServiceImpl;
import com.betacom.services.implementations.SizeServiceImpl;

import lombok.RequiredArgsConstructor;

import com.betacom.model.Category;

@Component
@RequiredArgsConstructor
public class ModelMappers {
	private final CategoryServiceImpl categoryS;
	private final CategoryRepository categoryR;
	private final ProductServiceImpl productS;
	private final SizeServiceImpl sizeS;
	
	
	public Product product(ProductRequest request) throws Exception{
		
		
		
		return Product.builder()
				.id(request.getId())
				.name(request.getName())
				.image(request.getImage())
				.description(request.getDescription())
				.category(category(categoryS.getCateoryModelById(request.getId())))
				.gender(Genders.valueOf(request.getGender()))
				.material(request.getMaterial())
				.price(request.getPrice())
				.build();
	}
	
	public Category category(CategoryRequest request) {
		
		return Category.builder()
				.id(request.getId())
				.category(request.getCategory())
				.build();
	}
	
	public Category category(Category request) {
		
		return Category.builder()
				.id(request.getId())
				.category(request.getCategory())
				.build();
	}
	
	public Size size(SizeRequest request) throws Exception {
		
		return Size.builder()
				.id(request.getProductId())
				.product(productS.getProductModelById(request.getProductId()))
				.size(Sizes.valueOf(request.getSize()))
				.quantity(request.getQuantity())
				.build();
	}
	
	
	
	
	
	
	
	

}
