package com.trungbeso.dreamshops.security.jwt;

import org.springframework.security.core.Authentication;

public interface IJwtUtils {
	String generateTokenForUser(Authentication authentication);

	//extract username form token
	String getUsernameFromToken(String token);

	boolean validateToken(String token);

	Authentication getAuthentication(String token);
}
