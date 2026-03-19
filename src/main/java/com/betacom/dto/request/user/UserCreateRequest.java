package com.betacom.dto.request.user;

import java.time.LocalDate;

import com.betacom.enums.Roles;

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
	@NotBlank(message = "Campo name mancante")
	private String name;

	@NotBlank(message = "Campo lastName mancante")
	private String lastName;

	@NotNull(message = "Campo birthday mancante")
	private LocalDate birthday;

	@Pattern(regexp = "^[A-Z0-9]{16}$", message = "Codice fiscale non valido")
	private String codiceFiscale;

	@NotBlank(message = "Campo email mancante")
	@Email
	private String email;

	@NotBlank(message = "Campo password mancante")
	private String password;

	@NotBlank(message = "Campo phone mancante")
	@Pattern(regexp = "^[0-9]{10}$", message = "Telefono non valido")
	private String phone;

	@NotNull(message = "Campo role mancante")
	private Roles role;
}
