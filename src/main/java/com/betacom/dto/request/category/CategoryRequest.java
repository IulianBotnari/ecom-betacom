package com.betacom.dto.request.category;

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
public class CategoryRequest {
	  @NotBlank(message = "Campo id non presente")
	  private Long id;	
	  @NotBlank(message = "Campo categoria non puo essere nullo")
	  private String category;
}
