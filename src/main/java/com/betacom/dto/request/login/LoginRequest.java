package com.betacom.dto.request.login;

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
public class LoginRequest {
	@NotBlank(message = "Campo email mancante")
	@Email
	private String email;

	@NotBlank(message = "Campo password mancante")
	private String password;
}
