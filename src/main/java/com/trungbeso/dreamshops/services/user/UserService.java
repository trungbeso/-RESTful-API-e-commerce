package com.trungbeso.dreamshops.services.user;

import com.trungbeso.dreamshops.dtos.UserDto;
import com.trungbeso.dreamshops.exception.ResourceNotFoundException;
import com.trungbeso.dreamshops.models.User;
import com.trungbeso.dreamshops.repositories.IUserRepository;
import com.trungbeso.dreamshops.request.UserCreateRequest;
import com.trungbeso.dreamshops.request.UserUpdateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService{

	IUserRepository userRepository;
	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
			  .orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public User create(UserCreateRequest request) {
		return Optional.of(request)
			  .filter(user -> !userRepository.existsByEmail(request.getEmail()))
			  .map(req -> {
				  User user = new User();
				  user.setEmail(request.getEmail());
				  user.setPassword(passwordEncoder.encode(request.getPassword()));
				  user.setFirstName(request.getFirstName());
				  user.setLastName(request.getLastName());
				  return userRepository.save(user);
			  }).orElseThrow(() -> new ResourceNotFoundException(request.getEmail() +" are already exists"));
	}

	@Override
	public User update(UserUpdateRequest request, Long userId) {
		return userRepository.findById(userId).map(existingUser -> {
			existingUser.setFirstName(request.getFirstName());
			existingUser.setLastName(request.getLastName());
			return userRepository.save(existingUser);
		}).orElseThrow(() -> new ResourceNotFoundException("User not found"));

	}

	@Override
	public void delete(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () -> {
			throw new ResourceNotFoundException("User not found");
		} );
	}

	@Override
	public UserDto convertToUserDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepository.findByEmail(email);
	}
}
