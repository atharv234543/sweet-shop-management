package com.sweetshop.api.auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

	private final Key key;
	private final long expirationMs;

	public JwtTokenProvider(@Value("${app.jwt.secret}") String secret,
	                        @Value("${app.jwt.expiration-ms}") long expirationMs) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.expirationMs = expirationMs;
	}

	public String generateToken(String username, String role) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expirationMs);
		return Jwts.builder()
			.setSubject(username)
			.addClaims(Map.of("role", role))
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String getUsernameFromJWT(String token) {
		return parseClaims(token).getSubject();
	}

	public String getRoleFromJWT(String token) {
		Object role = parseClaims(token).get("role");
		return role == null ? null : role.toString();
	}

	public boolean validateToken(String authToken) {
		try {
			parseClaims(authToken);
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}

	private Claims parseClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
