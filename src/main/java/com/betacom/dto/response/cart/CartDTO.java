package com.betacom.dto.response.cart;

import java.time.LocalDate;
import java.util.List;

import com.betacom.model.CartItem;
import com.betacom.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Builder
@ToString
public class CartDTO {
	

    private Long id;
    private User user;
    private List<CartItem> cartItems;
    private LocalDate createDate;

}
