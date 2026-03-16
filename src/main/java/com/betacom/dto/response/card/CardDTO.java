package com.betacom.dto.response.card;

import com.betacom.model.PaymentMethod;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CardDTO {
	
    private Long id;
    private PaymentMethod paymentMethod;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolder;

}
