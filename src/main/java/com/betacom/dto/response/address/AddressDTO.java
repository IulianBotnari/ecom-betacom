package com.betacom.dto.response.address;



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
public class AddressDTO {
	private Long id;
	private String city;
    private String street;
    private String civic;
    private String staircase;
    private String province;
    private String cap;
    private String country;
    private Long userId;
    private boolean residence;
    private boolean domicile;
    private boolean defaulAddress;
}
