package com.trungbeso.dreamshops.repositories;

import com.trungbeso.dreamshops.models.CartItem;
import com.trungbeso.dreamshops.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {

  void deleteAllByCartId(Long id);
}