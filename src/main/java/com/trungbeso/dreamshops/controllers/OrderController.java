package com.trungbeso.dreamshops.controllers;

import com.trungbeso.dreamshops.dtos.OrderDto;
import com.trungbeso.dreamshops.exception.ResourceNotFoundException;
import com.trungbeso.dreamshops.response.ApiResponse;
import com.trungbeso.dreamshops.services.order.IOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {

	IOrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<ApiResponse> create(@RequestParam Long userId) {
		try {
			OrderDto orderDto = orderService.placeOrder(userId);
			return ResponseEntity.ok(new ApiResponse("Item Order Success", orderDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error :", e.getMessage()));
		}
	}

	@GetMapping("/{orderId}/order")
	public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
		try {
			List<OrderDto> orders = orderService.getUserOrders(orderId);
			return ResponseEntity.ok(new ApiResponse("Item Order Success", orders));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error :", e.getMessage()));
		}
	}

	@GetMapping("/{userId}/order")
	public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
		try {
			List<OrderDto> orders = orderService.getUserOrders(userId);
			return ResponseEntity.ok(new ApiResponse("Item Order Success", orders));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error :", e.getMessage()));
		}
	}
}
