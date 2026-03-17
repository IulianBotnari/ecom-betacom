package com.betacom.dto.request.user;

import java.time.LocalDate;

import com.betacom.dto.request.address.AddressRequest;

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
public class UserRequest {
	 	private String name;
	    private String lastName;
	    private LocalDate birthday;
	    private String codiceFiscale;
	    private String email;
	    private String password;
	    private String phone;
	    private String role;
}
	