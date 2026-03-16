package com.betacom.dto.response.address;



import com.betacom.model.User;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AddressDTO {
	private Long id;
	private String city;
    private String street;
    private String civic;
    private String staircase;
    private String province;
    private String cap;
    private String country;
    private User user;
    private boolean residence;
    private boolean domicile;
    private boolean defaulAddress;
}
