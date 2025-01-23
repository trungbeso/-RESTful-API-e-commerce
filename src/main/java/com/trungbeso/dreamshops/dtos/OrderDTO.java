package com.trungbeso.dreamshops.dtos;

import com.trungbeso.dreamshops.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
	Long orderId;

	LocalDate orderDate;

	BigDecimal totalAmount;

	OrderStatus orderStatus;

	Set<OrderItemDTO> items;
}
