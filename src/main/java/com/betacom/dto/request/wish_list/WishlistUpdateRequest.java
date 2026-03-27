package com.betacom.dto.request.wish_list;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistUpdateRequest {
	private Long id;
    private Long productId;
}