package com.example.gateway.ratelimiter;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.example.gateway.repository.RedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

	private static final int LIMIT = 100;

	private final RedisRepository redisRepository;

	public boolean allowRequest(String userId) {

		String key = "rate_limit:" + userId;

		Long count = redisRepository.increment(key);

		if (count == 1) {

			redisRepository.expire(key, Duration.ofMinutes(1));
		}

		return count <= LIMIT;
	}
}