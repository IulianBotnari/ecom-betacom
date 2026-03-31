package com.betacom.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CategoryRequestNoID {

	  @NotBlank(message = "Campo categoria non puo essere nullo")
	  private String category;
	  
	  @NotNull(message = "Devi specificare se la categoria è visibile in home")
	  private Boolean isView;
}
