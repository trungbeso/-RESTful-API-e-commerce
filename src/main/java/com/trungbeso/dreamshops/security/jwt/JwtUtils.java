package com.trungbeso.dreamshops.security.jwt;

import com.trungbeso.dreamshops.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtils implements IJwtUtils {

	@Value("${auth.token.jwtSecret}")
	private String jwtSecret;

	@Value("${auth.token.expiration}")
	private int expirationTime;

	@Override
	public String generateTokenForUser(Authentication authentication) {
		//get user login
		ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

		//get role
		List<String> roles = userPrincipal.getAuthorities()
			  .stream()
			  .map(GrantedAuthority::getAuthority)
			  .toList();

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
	@Override
	public String getUsernameFromToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		Claims claims = Jwts.parser()
			  .verifyWith(key)
			  .build()
			  .parseSignedClaims(token)
			  .getPayload();

		return claims.getSubject();
	}

	@Override
	public boolean validateToken(String token) {
		if (token == null || token.isEmpty()) {
			throw new JwtException("Token can not be null or empty");
		}
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		Claims claims;

		try {
			claims = Jwts.parser()
				  .verifyWith(key)
				  .build()
				  .parseSignedClaims(token)
				  .getPayload();
			if (claims == null) {
				throw new JwtException("claims can not be null");
			}
			//validate roles
			List<String> roles = claims.get("roles", List.class);
			if (roles == null || roles.isEmpty()) {
				throw new JwtException("Access denied: User does not have admin privileges");
			}

		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new JwtException("Invalid JWT token", e);
		}
		return true;
	}

	@Override
	public Authentication getAuthentication(String token) {
		if (token == null) {
			return null;
		}
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

		try {
			Claims claims = Jwts.parser()
				  .verifyWith(key)
				  .build()
				  .parseSignedClaims(token)
				  .getPayload();

			String roles = claims.get("roles").toString();

			Set<GrantedAuthority> authorities = Set.of(roles.split(",")).stream()
				  .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

			User principle = new User(claims.getSubject(), "", authorities);
			return new UsernamePasswordAuthenticationToken(principle, token, authorities);
		} catch (ExpiredJwtException e) {
			return null;
		}
	}

}
