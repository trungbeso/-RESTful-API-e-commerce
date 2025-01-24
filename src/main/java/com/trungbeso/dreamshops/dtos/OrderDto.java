package com.trungbeso.dreamshops.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {
	Long id;

	Long userId;

	LocalDateTime orderDate;

	BigDecimal totalAmount;

	String status;

	Set<OrderItemDto> items;
}
