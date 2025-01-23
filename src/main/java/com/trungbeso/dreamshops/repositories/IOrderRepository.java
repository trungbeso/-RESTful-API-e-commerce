package com.trungbeso.dreamshops.repositories;

import com.trungbeso.dreamshops.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUserId(Long userId);
}
