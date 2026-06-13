package com.example.gateway.exception;

public class RateLimitExceededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3064673494318788922L;

	public RateLimitExceededException(String message) {

		super(message);
	}
}