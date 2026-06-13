package com.example.gateway.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

	private JwtUtil jwtUtil;

	@BeforeEach
	void setup() {
		jwtUtil = new JwtUtil();
	}

	@Test
	void shouldGenerateToken() {

		String token = jwtUtil.generateToken("rohit");

		assertNotNull(token);
	}

	@Test
	void shouldExtractUsername() {

		String token = jwtUtil.generateToken("rohit");

		String username = jwtUtil.extractUsername(token);

		assertEquals("rohit", username);
	}

	@Test
	void shouldValidateToken() {

		String token = jwtUtil.generateToken("rohit");

		assertTrue(jwtUtil.validateToken(token));
	}
}