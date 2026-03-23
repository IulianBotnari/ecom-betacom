package com.betacom.dto.response.review;

import java.time.LocalDate;

import com.betacom.model.Product;
import com.betacom.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
 @Getter
 @Setter
 @Builder
 @ToString
 @AllArgsConstructor
 @NoArgsConstructor
public class ReviewDTO {
	
    private Long id;

    private User user;

    private Product product;

    private Integer rating;

    private String review;

    private LocalDate date;

}
