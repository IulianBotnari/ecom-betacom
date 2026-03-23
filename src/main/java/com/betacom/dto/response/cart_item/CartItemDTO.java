package com.betacom.dto.response.cart_item;

import com.betacom.model.Cart;
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
public class CartItemDTO {
	 	private Long id;
	    private Integer quantity;
	    private Cart cart;
	    private Product product;
	    private Size size;

}
