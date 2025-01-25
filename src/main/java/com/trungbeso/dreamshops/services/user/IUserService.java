package com.trungbeso.dreamshops.services.user;

import com.trungbeso.dreamshops.dtos.UserDto;
import com.trungbeso.dreamshops.models.User;
import com.trungbeso.dreamshops.request.UserCreateRequest;
import com.trungbeso.dreamshops.request.UserUpdateRequest;

public interface IUserService {
	User getUserById(Long userId);

	User create(UserCreateRequest request);

	User update(UserUpdateRequest request, Long userId);

	void delete(Long userId);

	UserDto convertToUserDto(User user);

	User getAuthenticatedUser();
}
