package com.trungbeso.dreamshops.security.jwt;

import com.trungbeso.dreamshops.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

	@Value("${auth.token.jwtSecret}")
	private String jwtSecret;

	@Value("${auth.token.expiration}")
	private int expirationTime;

	public String generateTokenForUser(Authentication authentication) {
		//get user login
		ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

		//get role
		List<String> roles = userPrincipal.getAuthorities()
			  .stream()
			  .map(GrantedAuthority::getAuthority).toList();

		LocalDateTime expirationAt = LocalDateTime.now().plusMinutes(expirationTime);

		Date expiration = Date.from(expirationAt.atZone(ZoneId.systemDefault()).toInstant());

		return Jwts.builder()
			  .subject(userPrincipal.getUsername())
			  // claim mean information want to pass for user
			  .claim("id", userPrincipal.getId())
			  .claim("roles", roles)
			  .expiration(expiration)
			  .signWith(key())
			  .compact();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	//extract username form token
	public String getUsernameFromToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		Claims claims = Jwts.parser()
			  .verifyWith(key)
			  .build()
			  .parseSignedClaims(token)
			  .getPayload();

		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		Claims claims;

		try {
			claims = Jwts.parser()
				  .verifyWith(key)
				  .build()
				  .parseSignedClaims(token)
				  .getPayload();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new JwtException("Invalid JWT token", e);
		}

		// for reset password UC
		String jsonTokenId = claims.getId();
		String email = claims.getSubject();

		return true;
	}

}
