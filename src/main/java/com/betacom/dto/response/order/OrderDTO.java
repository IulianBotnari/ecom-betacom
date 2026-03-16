package com.betacom.dto.response.order;


import java.time.LocalDate;
import java.util.List;

import com.betacom.enums.OrderStatus;
import com.betacom.model.Address;
import com.betacom.model.OrderDetail;
import com.betacom.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OrderDTO {
	
    private Long id;

    private User user;

    private LocalDate date;
    
    private OrderStatus status; 

    private Double total;

    private Address shippingAddress;

    private List<OrderDetail> details;


}
