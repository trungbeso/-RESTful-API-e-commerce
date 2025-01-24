package com.trungbeso.dreamshops.services.cart;

import com.trungbeso.dreamshops.models.Cart;
import com.trungbeso.dreamshops.models.User;

import java.math.BigDecimal;

public interface ICartService {
	Cart getCart(Long id);

	void clearCart(Long id);

	BigDecimal getTotalPrice(Long id);

	Cart getCartByUser(Long userId);

	Cart initializeNewCart(User user);

	Cart getCartByUserId(Long userId);
}
