package com.trungbeso.dreamshops.dtos;

import com.trungbeso.dreamshops.models.CartItem;
import com.trungbeso.dreamshops.models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {
	Long id;

	BigDecimal totalAmount = BigDecimal.ZERO;

	Set<CartItemDto> items;

	User user;
}

