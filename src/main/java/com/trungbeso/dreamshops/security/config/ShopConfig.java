package com.trungbeso.dreamshops.security.config;

import com.trungbeso.dreamshops.security.jwt.AuthTokenFilter;
import com.trungbeso.dreamshops.security.jwt.JwtAuthEntryPoint;
import com.trungbeso.dreamshops.security.jwt.JwtUtils;
import com.trungbeso.dreamshops.security.user.ShopUserDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ShopConfig {

	private static final List<String> SECURED_URL =
		  List.of("/api/v1/carts/**", "api/v1/cartItems/**");

	ShopUserDetailsService userDetailsService;
	JwtAuthEntryPoint jwtAuthEntryPoint;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.csrf(AbstractHttpConfigurer::disable)
				  .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
				  .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				  .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URL.toArray(String[]::new)).authenticated()
					    .anyRequest().permitAll());
			http.authenticationProvider(daoAuthenticationProvider());
			http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
			return http.build();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthTokenFilter authTokenFilter(){
		return AuthTokenFilter.builder().build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}


}
