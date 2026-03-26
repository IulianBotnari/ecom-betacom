package com.betacom.dto.response.product;

import java.util.List;

import com.betacom.enums.Genders;
import com.betacom.model.Category;
import com.betacom.model.Review;
import com.betacom.model.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {
	
	
	private Long id;
	
	private String name;
	
	private String image;
	
	private String description;
	
	private Category category;
	
	private Genders gender;
	
	private String material;
	
	private Double price;
	
	private Double discount;
	
	private Double discountPercentage;
	
	private List<Size> sizes;
	
	private List<Review> reviews;

}
