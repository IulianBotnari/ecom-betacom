package com.betacom.dto.request.order;

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
public class OrderRequest {
	private Long id;
	private Long userId;
    private Long shippingAddress;
    private String status; 
}
