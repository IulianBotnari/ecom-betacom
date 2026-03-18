package com.betacom.dto.request.review;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {
    private Long userId;
    private Long productId;
    private Integer rating;
    private String review;
}