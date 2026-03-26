package com.betacom.dto.response.order;


import java.time.LocalDate;
import java.util.List;

import com.betacom.dto.response.address.AddressDTO;
import com.betacom.dto.response.ordered_items_details.OrderedItemsDetailsDTO;
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

    private Long userId;

    private LocalDate date;
    
    private OrderStatus status; 

    private Double orderPrice;

    private AddressDTO shippingAddress;

    private List<OrderedItemsDetailsDTO> details;


}
