package com.Ecommerce.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartAddRequest {
	@NotNull
	private Long variantId;

	@NotNull
	@Min(1)
	private Integer quantity;
}