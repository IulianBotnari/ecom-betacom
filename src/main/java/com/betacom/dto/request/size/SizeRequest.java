package com.betacom.dto.request.size;

import com.betacom.dto.request.address.AddressCreateRequest;

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

	private Long id;

    private Long productId;
	@NotBlank(message = "Manca campo size")
    private String size;
	
    private Integer quantity;

}
