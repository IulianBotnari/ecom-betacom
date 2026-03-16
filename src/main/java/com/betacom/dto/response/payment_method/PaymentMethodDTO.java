package com.betacom.dto.response.payment_method;

import com.betacom.model.Card;
import com.betacom.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PaymentMethodDTO {
	 
		private Long id;
		
		private User user;

	    private String description; 
	    
	    private Card card;

}
