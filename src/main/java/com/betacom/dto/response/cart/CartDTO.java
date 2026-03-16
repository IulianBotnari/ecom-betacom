package com.betacom.dto.response.cart;

import java.time.LocalDate;
import java.util.List;

import com.betacom.model.CartItem;
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
public class CartDTO {
	

    private Long id;
    private User user;
    private List<CartItem> cartItems;
    private LocalDate createDate;

}
