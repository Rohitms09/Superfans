package com.example.gateway.ratelimiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;

@ExtendWith(MockitoExtension.class)
class RateLimitFilterTest {

	@Mock
	private RateLimiterService rateLimiterService;

	@Mock
	private FilterChain filterChain;

	@InjectMocks
	private RateLimitFilter rateLimitFilter;

	@Test
	void shouldAllowRequest() throws Exception {

		Authentication authentication = mock(Authentication.class);

		when(authentication.isAuthenticated()).thenReturn(true);

		when(authentication.getName()).thenReturn("rohit");

		SecurityContextHolder.getContext().setAuthentication(authentication);

		when(rateLimiterService.allowRequest("rohit")).thenReturn(true);

		rateLimitFilter.doFilterInternal(new MockHttpServletRequest(), new MockHttpServletResponse(), filterChain);

		verify(rateLimiterService).allowRequest("rohit");

		verify(filterChain).doFilter(any(), any());
	}

	@Test
	void shouldRejectRequest() throws Exception {

		Authentication authentication = mock(Authentication.class);

		when(authentication.isAuthenticated()).thenReturn(true);

		when(authentication.getName()).thenReturn("rohit");

		SecurityContextHolder.getContext().setAuthentication(authentication);

		when(rateLimiterService.allowRequest("rohit")).thenReturn(false);

		MockHttpServletResponse response = new MockHttpServletResponse();

		rateLimitFilter.doFilterInternal(new MockHttpServletRequest(), response, filterChain);

		assertEquals(429, response.getStatus());

		verify(rateLimiterService).allowRequest("rohit");

		verify(filterChain, never()).doFilter(any(), any());
	}
}