package com.sweetshop.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	private String role; // optional: defaults to USER
}
