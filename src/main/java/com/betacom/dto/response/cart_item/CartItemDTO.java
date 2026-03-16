package com.betacom.dto.response.cart_item;

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
	    private Long cartId;
	    private Long productId;
	    private Long sizeId;

}
