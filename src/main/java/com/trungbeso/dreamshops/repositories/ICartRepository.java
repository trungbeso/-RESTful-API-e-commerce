package com.trungbeso.dreamshops.repositories;

import com.trungbeso.dreamshops.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart, Long> {

	Cart findByUserId(Long userId);
}
