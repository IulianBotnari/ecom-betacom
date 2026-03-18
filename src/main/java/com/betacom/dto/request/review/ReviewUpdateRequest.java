package com.betacom.dto.request.review;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequest {
    private Long id;          
    private Integer rating;   
    private String review;    
}