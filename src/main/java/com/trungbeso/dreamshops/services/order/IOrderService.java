package com.trungbeso.dreamshops.services.order;

import com.trungbeso.dreamshops.dtos.OrderDto;

import java.util.List;

public interface IOrderService {
	OrderDto placeOrder(Long userId);

	OrderDto getOrder(Long orderId);

	List<OrderDto> getUserOrders(Long userId);
}
