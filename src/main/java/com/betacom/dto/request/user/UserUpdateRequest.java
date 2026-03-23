package com.betacom.dto.request.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
public class UserUpdateRequest {
	private Long id;
 	private String name;
   	private String lastName;
    private LocalDate birthday;
    
    @Pattern(regexp = "^[A-Z0-9]{16}$", message = "Codice fiscale non valido")
    private String codiceFiscale;
    
    @Email
    private String email;
    private String password;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "Telefono non valido")
    private String phone;
    private String role;
}
