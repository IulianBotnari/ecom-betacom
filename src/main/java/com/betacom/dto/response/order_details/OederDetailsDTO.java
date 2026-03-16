package com.betacom.dto.response.order_details;

import com.betacom.model.Order;
import com.betacom.model.Product;
import com.betacom.model.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OederDetailsDTO {
	
    private Long id;

    private Order order;

    private Product product;
    
    private Size size;

    private Integer quantity;

    private Double totalPrice;

}
