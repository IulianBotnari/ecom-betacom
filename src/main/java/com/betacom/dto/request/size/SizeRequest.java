package com.betacom.dto.request.size;

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
public class SizeRequest {

    private Long productId;
    private String size;
    private Integer quantity;

}
