package com.betacom.dto.response.cart_item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CartItemDTO {
	 	private Long id;
	    private Integer quantity;
	    private Long cartId;
	    private Long productId;
	    private Long sizeId;

}
