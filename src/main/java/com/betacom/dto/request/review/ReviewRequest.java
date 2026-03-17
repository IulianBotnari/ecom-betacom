package com.betacom.dto.request.review;

import com.betacom.dto.request.address.AddressRequest;

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
public class ReviewRequest {
	private Long userId;
    private Long productId;

    private Integer rating;
    private String review;
}
