package com.example.gateway.repository;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

	private final StringRedisTemplate redisTemplate;

	public Long increment(String key) {

		return redisTemplate.opsForValue().increment(key);
	}

	public void expire(String key, Duration duration) {

		redisTemplate.expire(key, duration);
	}
}
