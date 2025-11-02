package com.Ecommerce.Auth;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	@NotBlank(message = "Name required")
	private String name;

	@Email(message = "Invalid email")
	@NotBlank
	private String email;

	@Size(min = 6, message = "Password min 6 chars")
	private String password;

	private Set<String> roles;
}