package com.betacom.dto.response.card;

import com.betacom.model.PaymentMethod;


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
public class CardDTO {
	
    private Long id;
    private PaymentMethod paymentMethod;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolder;

}
