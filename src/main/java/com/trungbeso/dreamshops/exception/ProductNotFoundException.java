package com.trungbeso.dreamshops.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class ProductNotFoundException extends RuntimeException {
	public ProductNotFoundException(String message) {
		super(message);
	}
}
