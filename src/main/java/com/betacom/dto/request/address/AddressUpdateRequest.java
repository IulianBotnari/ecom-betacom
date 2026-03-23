package com.betacom.dto.request.address;

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
public class AddressUpdateRequest {
	private Long id;
	private Long userId;
    private String city;
    private String street;
    private String civic;
    private String staircase;
    private String province;
    private String cap;
    private String country;
    private Boolean residence;
    private Boolean domicile;
    private Boolean defaultAddress;
}
