package com.trungbeso.dreamshops.security.jwt;

import com.trungbeso.dreamshops.security.user.ShopUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Builder
public class AuthTokenFilter extends OncePerRequestFilter {

	private final IJwtUtils jwtUtils;
	private final ShopUserDetailsService userDetailsService;

	@Autowired
	public AuthTokenFilter(IJwtUtils jwtUtils, ShopUserDetailsService userDetailsService) {
		if (jwtUtils == null) {
			throw new IllegalArgumentException("jwtUtils cannot be null");
		}
		if (userDetailsService == null) {
			throw new IllegalArgumentException("userDetailsService cannot be null");
		}
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
	                                @NonNull HttpServletResponse response,
	                                @NonNull FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (StringUtils.hasText(jwt)) {

				if (jwtUtils.validateToken(jwt)) {
					String username = jwtUtils.getUsernameFromToken(jwt);
					//extract username from database

					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					//authenticate user
					Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		} catch (JwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(e.getMessage() + " : Invalid or expired token, you may login again!");
			return;
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(e.getMessage());
			return;
		}
		filterChain.doFilter(request, response);
	}

	//cap-cha request from claims
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}
		return null;
	}
}
