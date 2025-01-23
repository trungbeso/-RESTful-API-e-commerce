package com.trungbeso.dreamshops.dtos;

import com.trungbeso.dreamshops.models.Cart;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
	 Long id;

	 String firstName;

	 String lastName;

	 String email;

	 String password;

	 Cart cart;

	 List<OrderDto> orders;
}
