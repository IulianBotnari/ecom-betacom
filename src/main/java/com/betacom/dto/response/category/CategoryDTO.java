package com.betacom.dto.response.category;

import java.util.List;

import com.betacom.model.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CategoryDTO {
	
    private Long id;
    private String category;
    private List<Product> products;

}
