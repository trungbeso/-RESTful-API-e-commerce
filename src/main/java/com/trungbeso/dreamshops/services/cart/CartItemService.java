package com.trungbeso.dreamshops.services.cart;

import com.trungbeso.dreamshops.exception.ResourceNotFoundException;
import com.trungbeso.dreamshops.models.Cart;
import com.trungbeso.dreamshops.models.CartItem;
import com.trungbeso.dreamshops.models.Product;
import com.trungbeso.dreamshops.repositories.ICartItemRepository;
import com.trungbeso.dreamshops.repositories.ICartRepository;
import com.trungbeso.dreamshops.services.product.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService implements ICartItemService {

	ICartItemRepository cartItemRepository;
	ICartRepository cartRepository;

	IProductService productService;
	ICartService cartService;


	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		// get the cart
		// get the product
		// check if the product already in the cart
		// if yes, then increase the quantity with the requested quantity
		// if no, then initiate a new CartItem entry
		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProductById(productId);
		CartItem cartItem = cart.getItems()
			  .stream()
			  .filter(item -> item.getProduct().getId().equals(productId))
			  .findFirst().orElse(new CartItem());
		if (cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		CartItem itemToRemove = getCartItem(cartId, productId);
		cart.removeItem(itemToRemove);
		cartRepository.save(cart);
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);
		cart.getItems()
			  .stream()
			  .filter(item -> item.getProduct().getId().equals(productId))
			  .findFirst().ifPresent(item -> {
				  item.setQuantity(quantity);
				  item.setUnitPrice(item.getProduct().getPrice());
				  item.setTotalPrice();
			  });
		BigDecimal totalAmount = cart.getItems()
			  .stream()
			  .map(CartItem::getTotalPrice)
			  .reduce(BigDecimal.ZERO, BigDecimal::add);
		cart.setTotalAmount(totalAmount);
		cartRepository.save(cart);
	}


	@Override
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		return cart.getItems()
			  .stream()
			  .filter(item -> item.getProduct().getId().equals(productId))
			  .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
	}
}
