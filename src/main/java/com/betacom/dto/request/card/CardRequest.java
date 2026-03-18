package com.betacom.dto.request.card;



import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
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
public class CardRequest {
		
	private Long id;
	
	@Pattern(regexp = "^[0-9]{16}$", message = "Il numero della carta deve essere di 16 cifre")
	private String cardNumber;

	@Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Formato scadenza non valido (MM/YY)")
	private String expiryDate;

	@Pattern(regexp = "^[0-9]{3}$", message = "Il CVV deve essere di 3 cifre")
	private String cvv;
	
	
	private String cardHolder;
}	
