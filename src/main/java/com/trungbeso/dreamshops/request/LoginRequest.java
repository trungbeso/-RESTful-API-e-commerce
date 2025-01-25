package com.trungbeso.dreamshops.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message = "Email must not be empty")
	private String email;

	@NotBlank(message = "Password must not be empty")
	private String password;
}
