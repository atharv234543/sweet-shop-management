package com.sweetshop.api.auth.controller;

import com.sweetshop.api.auth.dto.*;
import com.sweetshop.api.auth.service.JwtTokenProvider;
import com.sweetshop.api.user.model.User;
import com.sweetshop.api.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
		if (userRepository.existsByUsername(req.getUsername())) {
			return ResponseEntity.badRequest().body("Username already exists");
		}
		String role = (req.getRole() == null || req.getRole().isBlank()) ? "USER" : req.getRole().toUpperCase();
		User user = User.builder()
			.username(req.getUsername())
			.password(passwordEncoder.encode(req.getPassword()))
			.role(role)
			.build();
		userRepository.save(user);
		String token = tokenProvider.generateToken(user.getUsername(), user.getRole());
		return ResponseEntity.ok(new AuthResponse(token, user.getRole()));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		User user = userRepository.findByUsername(req.getUsername()).orElseThrow();
		String token = tokenProvider.generateToken(user.getUsername(), user.getRole());
		return ResponseEntity.ok(new AuthResponse(token, user.getRole()));
	}
}
