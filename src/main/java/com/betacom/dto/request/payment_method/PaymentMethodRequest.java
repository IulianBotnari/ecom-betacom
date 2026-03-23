package com.betacom.dto.request.payment_method;

import com.betacom.dto.request.card.CardRequest;
import com.betacom.model.Card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodRequest {

	private Long id;
    private Long userId;
    private CardRequest card;
    private String description;

}	
