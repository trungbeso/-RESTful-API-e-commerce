package com.trungbeso.dreamshops.response;

import lombok.*;

@Data
public class ApiResponse {
	private String message;
	private Object data;

	public ApiResponse(String message, Object data) {
		this.message = message;
		this.data = data;
	}

	public ApiResponse() {
	}
}
