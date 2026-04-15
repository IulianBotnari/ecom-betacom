package com.betacom.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProudctUpdate {

	private Long id;

	private String name;

    private String description;

    private Double price;
    
    private Double discount;
    
    private Double discountPercentage;

    private Long categoryId;

    private String gender;

    private String image;
	
    private String material;
}
