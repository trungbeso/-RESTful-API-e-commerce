package com.trungbeso.dreamshops.services.cart;

import com.trungbeso.dreamshops.exception.ResourceNotFoundException;
import com.trungbeso.dreamshops.models.Cart;
import com.trungbeso.dreamshops.repositories.ICartItemRepository;
import com.trungbeso.dreamshops.repositories.ICartRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService implements ICartService{

	ICartRepository cartRepository;
	ICartItemRepository cartItemRepository;

	AtomicLong cartIdGenerator = new AtomicLong(0);


	@Override
	public Cart getCart(Long id) {
		Cart cart = cartRepository.findById(id)
			  .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepository.save(cart);
	}


	@Transactional
	@Override
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cartRepository.deleteById(id);

	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}

	@Override
	public Long initializeNewCart() {
		Cart newCart = new Cart();
//		Long newCartId = cartIdGenerator.incrementAndGet();
//		newCart.setId(newCartId);
		return cartRepository.save(newCart).getId();
	}

	@Override
	public Cart getCartByUserId(Long userId) {
		return cartRepository.findByUserId(userId);
	}
}
