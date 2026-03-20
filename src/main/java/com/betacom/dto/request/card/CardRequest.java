package com.betacom.dto.request.card;



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
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
    
    
    private Long id;

    
    @NotBlank(message = "Il titolare della carta è obbligatorio")
    private String cardHolder;

    @NotBlank(message = "Il numero della carta è obbligatorio")
    @Pattern(regexp = "^[0-9]{16}$", message = "Il numero della carta deve essere di 16 cifre")
    private String cardNumber;

    @NotBlank(message = "La data di scadenza è obbligatoria")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Formato scadenza non valido (MM/YY)")
    private String expiryDate;

    // Il CVV: presente nella Request per la validazione, ma non verrà mai salvato nel DB
    @NotBlank(message = "Il CVV è obbligatorio")
    @Pattern(regexp = "^[0-9]{3}$", message = "Il CVV deve essere di 3 cifre")
    private String cvv;
}
