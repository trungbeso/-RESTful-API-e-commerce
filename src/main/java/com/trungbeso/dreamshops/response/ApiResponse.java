package com.trungbeso.dreamshops.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
	private String message;
	private Object data;
}
