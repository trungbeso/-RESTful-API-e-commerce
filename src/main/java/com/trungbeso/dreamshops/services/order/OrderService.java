package com.trungbeso.dreamshops.services.order;

import com.trungbeso.dreamshops.dtos.OrderDto;
import com.trungbeso.dreamshops.enums.OrderStatus;
import com.trungbeso.dreamshops.exception.ResourceNotFoundException;
import com.trungbeso.dreamshops.models.Cart;
import com.trungbeso.dreamshops.models.Order;
import com.trungbeso.dreamshops.models.OrderItem;
import com.trungbeso.dreamshops.models.Product;
import com.trungbeso.dreamshops.repositories.IOrderRepository;
import com.trungbeso.dreamshops.repositories.ProductRepository;
import com.trungbeso.dreamshops.services.cart.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IOrderService{

	IOrderRepository orderRepository;
	ProductRepository productRepository;
	CartService cartService;
	ModelMapper modelMapper;

	@Override
	public OrderDto placeOrder(Long userId) {
		Cart cart = cartService.getCartByUserId(userId);
		Order order = createOrder(cart);
		List<OrderItem> orderItemList = createOrderItems(order, cart);
		order.setOrderItems(new HashSet<>(orderItemList));
		order.setTotalAmount(calculateTotalAmount(orderItemList));
		Order orderSaved = orderRepository.save(order);

		//done payment -> delete cart
		cartService.clearCart(cart.getId());
		return convertToOrderDTO(orderSaved);
	}



	private Order createOrder(Cart cart) {
		Order order = new Order();
		//set the user
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	}


	private List<OrderItem> createOrderItems(Order order, Cart cart) {
		return cart.getItems().stream().map(cartItem -> {
			Product product = cartItem.getProduct();
			product.setInventory(product.getInventory() - cartItem.getQuantity());
			productRepository.save(product);
			return new OrderItem(
				  order,
				  product,
				  cartItem.getQuantity(),
				  cartItem.getUnitPrice()
			);
		}).toList();
	}

	private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
		return orderItemList.stream()
			  .map(item -> item.getPrice()
				    .multiply(new BigDecimal(item.getQuantity())))
			  .reduce(BigDecimal.ZERO, BigDecimal::add);
	}


	@Override
	public OrderDto getOrder(Long orderId) {
		var order = orderRepository.findById(orderId)
			  .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		return convertToOrderDTO(order);
	}

	@Override
	public List<OrderDto> getUserOrders(Long userId) {
		return orderRepository.findByUserId(userId)
			  .stream()
			  .map(this::convertToOrderDTO).toList();
	}

	private OrderDto convertToOrderDTO(Order order) {
		return modelMapper.map(order, OrderDto.class);
	}
}
