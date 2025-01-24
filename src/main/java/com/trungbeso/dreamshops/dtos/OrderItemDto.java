package com.trungbeso.dreamshops.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
//single product
public class OrderItemDto {
	Long productId;

	String productName;

	String productBrand;

	int quantity;

	BigDecimal price;
}
