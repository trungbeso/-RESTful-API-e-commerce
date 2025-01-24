package com.trungbeso.dreamshops.dtos;

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

	CartDto cart;

	List<OrderDto> orders;
}
