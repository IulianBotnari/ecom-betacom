package com.betacom.dto.request.payment_method;

import com.betacom.dto.request.address.AddressCreateRequest;

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

    private Long userId;
    private Long cardId;
    private String description;

}
