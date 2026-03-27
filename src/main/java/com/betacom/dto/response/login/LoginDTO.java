package com.betacom.dto.response.login;

import java.time.LocalDate;
import java.util.List;

import com.betacom.dto.response.address.AddressDTO;
import com.betacom.dto.response.order.OrderDTO;
import com.betacom.dto.response.payment_method.PaymentMethodDTO;
import com.betacom.dto.response.review.ReviewDTO;
import com.betacom.dto.response.user.UserDTO;
import com.betacom.dto.response.wish_list.WishListDTO;
import com.betacom.enums.Roles;

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
public class LoginDTO {
	private Long id;
	private String name;
    private String lastName;
    private String email;
    private String password;
    private Roles role;
}
