package com.Ecommerce.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

//	@PostMapping("/register")
//	public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
//		String token = authService.register(req.name, req.email, req.password, req.roles);
//		return ResponseEntity.ok(new JwtResponse(token));
//	}
//
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequest req) {
//		String token = authService.login(req.email, req.password);
//		return ResponseEntity.ok(new JwtResponse(token));
//	}
//
//	@Data
//	static class RegisterRequest {
//		String name;
//		String email;
//		String password;
//		Set<String> roles;
//	}
//
//	@Data
//	static class LoginRequest {
//		String email;
//		String password;
//	}
//
//	@Data
//	static class JwtResponse {
//		private final String token;
//	}

	@PostMapping("/register")
	public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest req) {
		String token = authService.register(req.getName(), req.getEmail(), req.getPassword(), req.getRoles());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthRequest req) {
		String token = authService.login(req.getEmail(), req.getPassword());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@Data
	@AllArgsConstructor
	static class JwtResponse {
		private final String token;
	}
}