package com.betacom.dto.response.wish_list;

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
public class WishListDTO {
	
    private Long id;

    private User user;

    private Product product;

    private LocalDate createDate;
}
