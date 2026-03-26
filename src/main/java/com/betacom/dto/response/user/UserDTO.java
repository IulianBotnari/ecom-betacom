package com.betacom.dto.response.user;

import java.time.LocalDate;
import java.util.List;

import com.betacom.dto.response.address.AddressDTO;
import com.betacom.dto.response.order.OrderDTO;
import com.betacom.dto.response.payment_method.PaymentMethodDTO;
import com.betacom.dto.response.review.ReviewDTO;
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
public class UserDTO {
		private Long id;
	    private LocalDate birthday;
	    private LocalDate createDate;
	    private LocalDate deleteDate;
	    private String codiceFiscale;
	    private String email;
	    private String lastName;
	    private String name;
	    private String password;
	    private String phone;
	    private Roles role;
	    private List<AddressDTO> addresses;
	    private List<PaymentMethodDTO> paymentMethods;
	    private List<OrderDTO> orders;
	    private List<ReviewDTO> reviews;

}
