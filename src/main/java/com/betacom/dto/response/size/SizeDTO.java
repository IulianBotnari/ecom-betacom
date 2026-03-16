package com.betacom.dto.response.size;

import com.betacom.enums.Sizes;
import com.betacom.model.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SizeDTO {

    private Long id;
    
    private Product product;

    private Sizes size; 

    private Integer quantity;

}
