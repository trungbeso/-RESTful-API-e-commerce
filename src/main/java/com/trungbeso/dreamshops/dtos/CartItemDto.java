package com.trungbeso.dreamshops.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemDto {
	Long id;

	Integer quantity;

	BigDecimal unitPrice;

	BigDecimal totalPrice;

	ProductDto product;
}
