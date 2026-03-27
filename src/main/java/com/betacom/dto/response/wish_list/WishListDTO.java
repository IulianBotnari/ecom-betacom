package com.betacom.dto.response.wish_list;

import java.time.LocalDate;

import com.betacom.dto.response.product.ProductsDTO;
import com.betacom.model.Product;
import com.betacom.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    private Long userId;

    private ProductsDTO productId;

    private LocalDate createDate;
}
