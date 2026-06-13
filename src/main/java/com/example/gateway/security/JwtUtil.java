package com.example.gateway.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET = "mysecretkeymysecretkeymysecretkey123456";

	private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

	public String generateToken(String username) {

		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 86400000)).signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public String extractUsername(String token) {

		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public boolean validateToken(String token) {

		try {
			extractUsername(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}