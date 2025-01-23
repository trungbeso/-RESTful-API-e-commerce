package com.trungbeso.dreamshops.services.order;

import com.trungbeso.dreamshops.models.Order;

import java.util.List;

public interface IOrderService {
	Order placeOrder(Long userId);

	Order getOrder(Long orderId);

	List<Order> getUserOrders(Long userId);
}
