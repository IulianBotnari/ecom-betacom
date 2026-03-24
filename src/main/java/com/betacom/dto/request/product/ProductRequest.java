package com.betacom.dto.request.product;

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
public class ProductRequest {

	private Long id;
	@NotBlank(message = "Campo name non puo essere vuoto")
	private String name;
	@NotBlank(message = "Campo description non puo essere vuoto")
    private String description;

    private Double price;

    private Long categoryId;
	@NotBlank(message = "Campo gender non puo essere vuoto")
    private String gender;
	@NotBlank(message = "Campo image non puo essere vuoto")
    private String image;
	@NotBlank(message = "Campo material non puo essere vuoto")
    private String material;

}
