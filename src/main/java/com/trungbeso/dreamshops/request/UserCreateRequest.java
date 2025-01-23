package com.trungbeso.dreamshops.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
	String firstName;

	String lastName;

	String email;

	String password;
}
