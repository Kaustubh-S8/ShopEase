package com.Ecommerce.Auth;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
		String token = authService.register(req.name, req.email, req.password, req.roles);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req) {
		String token = authService.login(req.email, req.password);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@Data
	static class RegisterRequest {
		String name;
		String email;
		String password;
		Set<String> roles;
	}

	@Data
	static class LoginRequest {
		String email;
		String password;
	}

	@Data
	static class JwtResponse {
		private final String token;
	}
}