package com.Ecommerce.Product;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
	@NotBlank(message = "Product name is required")
	private String name;

	private String description;

	@NotNull(message = "Price required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
	private BigDecimal price;

	@NotBlank(message = "Category is required")
	private String category;

	private String imageUrl;

	@NotNull(message = "variants required")
	@Size(min = 1, message = "At least one variant required")
	private List<VariantRequest> variants;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VariantRequest {
		@NotBlank(message = "Size required")
		private String size;
		@NotBlank(message = "Color required")
		private String color;
		@NotNull(message = "Quantity required")
		@Min(value = 0, message = "Quantity cannot be negative")
		private Integer quantity;
	}
}