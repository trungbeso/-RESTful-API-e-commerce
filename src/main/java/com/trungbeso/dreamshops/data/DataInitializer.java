package com.trungbeso.dreamshops.data;

import com.trungbeso.dreamshops.models.Role;
import com.trungbeso.dreamshops.models.User;
import com.trungbeso.dreamshops.repositories.IRoleRepository;
import com.trungbeso.dreamshops.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

	IUserRepository userRepository;
	PasswordEncoder passwordEncoder;
	IRoleRepository roleRepository;

	//when project run -> create 5 user for testing purpose
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");

		createDefaultUserIfNotExists();
		createDefaultRoleIfNotExists(defaultRoles);
		createDefaultAdminIfNotExists();

	}

	private void createDefaultUserIfNotExists() {
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		for (int i = 0; i <= 5; i++) {
			String defaultEmail = "user" + i + "@gmail.com";
			if (userRepository.existsByEmail(defaultEmail)) {
				continue;
			}
			User user = User.builder()
				  .firstName("The User ")
				  .lastName("User" + i)
				  .roles(Set.of(userRole))
				  .email(defaultEmail)
				  .password(passwordEncoder.encode("123123"))
				  .build();
			userRepository.save(user);
			System.out.println("Default user " + i + " created");
		}
	}

	private void createDefaultAdminIfNotExists() {
		Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
		for (int i = 0; i <= 2; i++) {
			String defaultEmail = "admin" + i + "@gmail.com";
			if (userRepository.existsByEmail(defaultEmail)) {
				continue;
			}
			User user = User.builder()
				  .firstName("Administrator ")
				  .lastName("Admin" + i)
				  .roles(Set.of(adminRole))
				  .email(defaultEmail)
				  .password(passwordEncoder.encode("123123"))
				  .build();
			userRepository.save(user);
			System.out.println("Default Admin " + i + " created");
		}
	}

	private void createDefaultRoleIfNotExists(Set<String> roles) {
		roles.stream()
			  .filter(role -> roleRepository.findByName(role).isEmpty())
			  .map(Role:: new).forEach(roleRepository::save);
	}
}
