package com.trungbeso.dreamshops.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {
	Long id;

	BigDecimal totalAmount;

	Set<CartItemDto> items;

}

