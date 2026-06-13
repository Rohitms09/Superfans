package com.example.gateway.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.gateway.dto.LoginRequest;
import com.example.gateway.dto.LoginResponse;
import com.example.gateway.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private AuthController authController;

	@Test
	void shouldReturnTokenForValidUser() {

		LoginRequest request = new LoginRequest();

		request.setUsername("rohit");
		request.setPassword("password");

		when(jwtUtil.generateToken("rohit")).thenReturn("sample-token");

		LoginResponse response = (LoginResponse) authController.login(request).getBody();

		assertEquals("sample-token", response.getToken());

		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

	@Test
	void shouldThrowExceptionForInvalidUser() {

		LoginRequest request = new LoginRequest();

		request.setUsername("abc");
		request.setPassword("xyz");

		doThrow(new RuntimeException("Invalid")).when(authenticationManager).authenticate(any());

		assertThrows(RuntimeException.class, () -> authController.login(request));
	}
}