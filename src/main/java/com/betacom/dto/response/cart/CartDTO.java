package com.betacom.dto.response.cart;

import java.time.LocalDate;
import java.util.List;

import com.betacom.dto.response.cart_item.CartItemDTO;
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
    private Long userId;
    private List<CartItemDTO> cartItems;
    private LocalDate createDate;

}
