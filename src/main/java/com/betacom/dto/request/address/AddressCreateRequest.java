package com.betacom.dto.request.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AddressCreateRequest {
	
	@NotBlank(message = "Campo city mancante")
    private String city;
	
	@NotBlank(message = "Campo street mancante")
    private String street;
    
	@NotBlank(message = "Campo civic mancante")
    private String civic;
    private String staircase;
    
    @NotBlank(message = "Campo province mancante")
    private String province;
    
    @NotBlank(message = "Campo cap mancante")
    private String cap;
    
    @NotBlank(message = "Campo country mancante")
    private String country;

    @NotNull(message = "Campo residence mancante")
    private Boolean residence;

    @NotNull(message = "Campo domicile mancante")
    private Boolean domicile;
    private Boolean defaultAddress;

    @NotNull(message = "Campo userId mancante")
	private Long userId;
}
