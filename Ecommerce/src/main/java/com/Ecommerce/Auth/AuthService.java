package com.Ecommerce.Auth;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Ecommerce.Security.JwtUtil;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepository;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authManager, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}

	public String register(String name, String email, String rawPassword, Set<String> roles) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email already in use");
		}

		User u = User.builder().name(name).email(email).password(passwordEncoder.encode(rawPassword)).roles(roles)
				.build();
		userRepository.save(u);
		return jwtUtil.generateToken(u.getEmail());
	}

	public String login(String email, String password) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		return jwtUtil.generateToken(email);
	}
}