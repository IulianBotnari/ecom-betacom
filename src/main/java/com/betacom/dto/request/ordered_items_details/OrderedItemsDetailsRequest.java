package com.betacom.dto.request.ordered_items_details;

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
public class OrderedItemsDetailsRequest {
	private Long orderId;
    private Long productId;
    private Long sizeId;
    private Integer quantity;
    private Double totalPrice;
}
