package com.betacom.dto.request.card;

import com.betacom.dto.response.address.AddressDTO;
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
public class CardRequest {
	private String cardHolder;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
}	
