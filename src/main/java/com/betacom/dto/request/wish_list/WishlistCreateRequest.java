package com.betacom.dto.request.wish_list;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistCreateRequest {
    private Long userId;
    private Long productId;
}