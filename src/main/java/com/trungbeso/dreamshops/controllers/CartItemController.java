package com.trungbeso.dreamshops.controllers;

import com.trungbeso.dreamshops.exception.ResourceNotFoundException;
import com.trungbeso.dreamshops.models.Image;
import com.trungbeso.dreamshops.response.ApiResponse;
import com.trungbeso.dreamshops.services.cart.ICartItemService;
import com.trungbeso.dreamshops.services.cart.ICartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.ResourceClosedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

	private final ICartItemService cartItemService;
	private final ICartService cartService;




	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
	                                                 @RequestParam Long productId,
	                                                 @RequestParam Integer quantity) {
		try {
			if (cartId == null) {
				cartId = cartService.initializeNewCart();
			}
			cartItemService.addItemToCart(cartId,  productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
		try {
			cartItemService.removeItemFromCart(cartId, itemId);
			return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/cart/{cartId}/item/{itemId}/update")
	public  ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
	                                                       @PathVariable Long itemId,
	                                                       @RequestParam Integer quantity) {
		try {
			cartItemService.updateItemQuantity(cartId, itemId, quantity);
			return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}

	}
}
