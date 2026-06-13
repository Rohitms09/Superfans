package com.example.gateway.ratelimiter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.gateway.repository.RedisRepository;

@ExtendWith(MockitoExtension.class)
class RateLimiterServiceTest {

	@Mock
	private RedisRepository redisRepository;

	@InjectMocks
	private RateLimiterService rateLimiterService;

	@Test
	void shouldAllowRequestWithinLimit() {

		when(redisRepository.increment(anyString())).thenReturn(50L);

		boolean result = rateLimiterService.allowRequest("rohit");

		assertTrue(result);
	}

	@Test
	void shouldRejectRequestAboveLimit() {

		when(redisRepository.increment(anyString())).thenReturn(101L);

		boolean result = rateLimiterService.allowRequest("rohit");

		assertFalse(result);
	}

	@Test
	void shouldSetExpiryForNewCounter() {

		when(redisRepository.increment(anyString())).thenReturn(1L);

		rateLimiterService.allowRequest("rohit");

		verify(redisRepository).expire(anyString(), eq(Duration.ofMinutes(1)));
	}
}