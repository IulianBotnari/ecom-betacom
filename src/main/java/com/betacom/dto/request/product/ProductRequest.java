package com.betacom.dto.request.product;

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
public class ProductRequest {
	private String name;
    private String description;
    private Double price;

    private Long categoryId;

    private String gender;
    private String image;
    private String material;
}
