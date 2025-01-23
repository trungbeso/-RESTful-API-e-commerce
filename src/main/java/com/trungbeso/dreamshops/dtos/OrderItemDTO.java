package com.trungbeso.dreamshops.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
//single product
public class OrderItemDTO {
	Long productId;

	String productName;

	int quantity;

	BigDecimal price;
}
