package com.betacom.dto.response.ordered_items_details;

import com.betacom.dto.response.order.OrderDTO;
import com.betacom.dto.response.product.ProductsDTO;
import com.betacom.dto.response.size.SizeDTO;
import com.betacom.model.Order;
import com.betacom.model.Product;
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
public class OrderedItemsDetailsDTO {
	
    private Long id;

    private OrderDTO order;

    private ProductsDTO product;
    
    private SizeDTO size;

    private Integer quantity;

    private Double totalPrice;

}
