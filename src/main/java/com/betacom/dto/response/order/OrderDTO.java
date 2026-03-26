package com.betacom.dto.response.order;


import java.time.LocalDate;
import java.util.List;

import com.betacom.enums.OrderStatus;
import com.betacom.model.Address;
import com.betacom.model.OrderedItemsDetails;
import com.betacom.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class OrderDTO {
	
    private Long id;

    @JsonIgnore
    private User user;

    private LocalDate date;
    
    private OrderStatus status; 

    private Double orderPrice;

    private Address shippingAddress;

    private List<OrderedItemsDetails> details;


}
