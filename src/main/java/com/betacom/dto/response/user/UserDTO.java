package com.betacom.dto.response.user;

import java.time.LocalDate;

import com.betacom.enums.Roles;

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
public class UserDTO {
		private Long id;
	    private LocalDate birthday;
	    private LocalDate createDate;
	    private LocalDate deleteDate;
	    private String codiceFiscale;
	    private String email;
	    private String lastName;
	    private String name;
	    private String password;
	    private String phone;
	    private Roles role;

}
