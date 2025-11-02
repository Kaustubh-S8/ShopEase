package com.Ecommerce.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
	@Email(message = "Invalid email")
	@NotBlank
	private String email;

	@NotBlank
	private String password;
}