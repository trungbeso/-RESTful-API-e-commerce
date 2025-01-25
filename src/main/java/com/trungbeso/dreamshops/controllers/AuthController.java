package com.trungbeso.dreamshops.controllers;

import com.trungbeso.dreamshops.request.LoginRequest;
import com.trungbeso.dreamshops.response.ApiResponse;
import com.trungbeso.dreamshops.response.JwtResponse;
import com.trungbeso.dreamshops.security.jwt.JwtUtils;
import com.trungbeso.dreamshops.security.user.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
	AuthenticationManager authenticationManager;
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		try {
			Authentication authentication = authenticationManager
				  .authenticate(new UsernamePasswordAuthenticationToken(
						 request.getEmail(), request.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateTokenForUser(authentication);
			ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();

			JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
			return ResponseEntity.ok(new ApiResponse("Login Successful",jwtResponse));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
