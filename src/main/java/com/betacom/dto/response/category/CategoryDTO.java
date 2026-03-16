package com.betacom.dto.response.category;

import java.util.List;

import com.betacom.model.Product;

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
public class CategoryDTO {
	
    private Long id;
    private String category;
    private List<Product> products;

}
