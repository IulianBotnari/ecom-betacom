package com.betacom.dto_mappers.map_model;
import java.net.Authenticator.RequestorType;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.betacom.dto.request.category.CategoryRequest;
import com.betacom.dto.request.category.CategoryRequestNoID;
import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.request.size.SizeRequest;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.enums.Genders;
import com.betacom.enums.Roles;
import com.betacom.enums.Sizes;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.model.User;

import lombok.RequiredArgsConstructor;

import com.betacom.model.Category;

@Component
@RequiredArgsConstructor
public class ModelMappers {
	
		
		public Product product(ProductRequest request, Category category) {
	        return Product.builder()
	              
	                .name(request.getName())
	                .image(request.getImage())
	                .description(request.getDescription())
	                .category(category)
	                .gender(Genders.valueOf(request.getGender()))
	                .material(request.getMaterial())
	                .price(request.getPrice())
	                .discount(request.getDiscount())
	                //.discountPercentage(request.getDiscountPercentage()))
	              
	                .build();
	    }
	
	public Category category(CategoryRequest request) {
        return Category.builder()
                .id(request.getId())
                .category(request.getCategory())
                .isView(request.getIsView())
                .build();
    }
	
	public Category category(CategoryRequestNoID request) {
        return Category.builder()
                .category(request.getCategory())
                .isView(request.getIsView())
                .build();
    }
	
	public Category category(Category request) {
		
		return Category.builder()
				.id(request.getId())
				.category(request.getCategory())
				.build();
	}
	
	public Size size(SizeRequest request, Product product) {
        return Size.builder()
                .id(request.getId()) 
                .product(product)    
                .size(Sizes.valueOf(request.getSize()))
                .quantity(request.getQuantity())
                .build();
    }
	
	public User user(UserCreateRequest request) {
		return User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .birthday(request.getBirthday())
                .codiceFiscale(request.getCodiceFiscale())
                .email(request.getEmail())
                .password(request.getPassword())
                .phone(request.getPhone())
                .role(request.getRole())
                .build();
	}
	
	
	
	
	
	
	

}
