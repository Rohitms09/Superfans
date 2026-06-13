package com.example.gateway.ratelimiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class ConcurrentRateLimiterTest {

	@Test
	void shouldHandleConcurrentRequests() {

		ExecutorService executor = Executors.newFixedThreadPool(20);

		AtomicInteger counter = new AtomicInteger();

		for (int i = 0; i < 100; i++) {

			executor.submit(() -> {
				counter.incrementAndGet();
			});
		}

		executor.shutdown();

		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			fail();
		}

		assertEquals(100, counter.get());
	}
}