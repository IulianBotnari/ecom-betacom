package com.betacom.dto.request.size;

import jakarta.validation.constraints.NotBlank;
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
public class SizeRequest {
	@NotBlank(message = "Manca campo id size")
	private Long id;
	@NotBlank(message = "Manca campo id prodotto")
    private Long productId;
	@NotBlank(message = "Manca campo size")
    private String size;
	@NotBlank(message = "Manca campo quantita")
    private Integer quantity;

}
