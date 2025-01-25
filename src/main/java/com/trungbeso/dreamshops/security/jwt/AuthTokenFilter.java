package com.trungbeso.dreamshops.security.jwt;

import com.trungbeso.dreamshops.security.user.ShopUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthTokenFilter extends GenericFilterBean {

	IJwtUtils jwtUtils;
	ShopUserDetailsService userDetailsService;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		String bearerToken = httpServletRequest.getHeader("Authorization");
		String jwtToken = null;

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			jwtToken = bearerToken.substring(7);
		}
		try {
			if (StringUtils.hasText(jwtToken)) {
				if (jwtUtils.validateToken(jwtToken)) {
					String username = jwtUtils.getUsernameFromToken(jwtToken);
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		} catch (JwtException e) {
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpServletResponse.getWriter().write(e.getMessage() + " : Invalid or expired token, you may login again!");
			return;
		} catch (Exception e) {
			httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			httpServletResponse.getWriter().write(e.getMessage());
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
