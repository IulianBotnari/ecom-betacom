package com.betacom.dto.request.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UserCreateRequest {
	@NotBlank
	private String name;

	@NotBlank
	private String lastName;

	@NotNull
	private LocalDate birthday;

	@Pattern(regexp = "^[A-Z0-9]{16}$", message = "Codice fiscale non valido")
	private String codiceFiscale;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	@Pattern(regexp = "^[0-9]{10}$", message = "Telefono non valido")
	private String phone;

	@NotBlank
	private String role;
}
