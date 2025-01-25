package com.trungbeso.dreamshops.security.user;

import com.trungbeso.dreamshops.models.User;
import com.trungbeso.dreamshops.repositories.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

	IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (email == null || email.isEmpty()) {
			throw new UsernameNotFoundException("Email cannot be null or empty");
		}
		User user = Optional.of(userRepository.findByEmail(email)).orElseThrow(() -> new UsernameNotFoundException(
			  "User name not found"));
		return ShopUserDetails.buildUserDetails(user);
	}
}
