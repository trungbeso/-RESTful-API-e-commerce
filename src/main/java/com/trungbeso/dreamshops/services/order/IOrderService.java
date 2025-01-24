package com.trungbeso.dreamshops.services.order;

import com.trungbeso.dreamshops.dtos.OrderDto;
import com.trungbeso.dreamshops.models.Order;

import java.util.List;

public interface IOrderService {
	Order placeOrder(Long userId);

	OrderDto getOrder(Long orderId);

	List<OrderDto> getUserOrders(Long userId);

	OrderDto convertToOrderDTO(Order order);
}
