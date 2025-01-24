package com.trungbeso.dreamshops.controllers;

import com.trungbeso.dreamshops.exception.ResourceNotFoundException;
import com.trungbeso.dreamshops.models.Cart;
import com.trungbeso.dreamshops.models.User;
import com.trungbeso.dreamshops.response.ApiResponse;
import com.trungbeso.dreamshops.services.cart.ICartItemService;
import com.trungbeso.dreamshops.services.cart.ICartService;
import com.trungbeso.dreamshops.services.user.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

	ICartItemService cartItemService;
	ICartService cartService;
	IUserService userService;

	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(
		  @RequestParam Long productId,
		  @RequestParam Integer quantity) {
		try {
			// in real life never fix cart by userId like this
			User user = userService.getUserById(1L); //manual param for testing
			Cart cart = cartService.initializeNewCart(user);

			cartItemService.addItemToCart(cart.getId(), productId, quantity);
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
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
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
