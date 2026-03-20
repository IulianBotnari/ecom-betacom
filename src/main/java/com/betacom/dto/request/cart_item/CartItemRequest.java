package com.betacom.dto.request.cart_item;

import com.betacom.dto.request.address.AddressCreateRequest;

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
public class CartItemRequest {
	
	private Long cartItemId;
	private Long cartId;
    private Long productId;
    private Long sizeId;
    private Integer quantity;
}
