package com.trungbeso.dreamshops.data;

import com.trungbeso.dreamshops.models.User;
import com.trungbeso.dreamshops.repositories.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

	IUserRepository userRepository;

	//when project run -> create 5 user for testing purpose
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		createDefaultUserIfNotExists();
	}

	private void createDefaultUserIfNotExists() {
		for (int i = 0; i <= 5; i++) {
			String defaultEmail = "user" + i + "@gmail.com";
			if (userRepository.existsByEmail(defaultEmail)) {
				continue;
			}
			User user = User.builder()
				  .firstName("The User ")
				  .lastName("User" + i)
				  .email(defaultEmail)
				  .password("123123")
				  .build();
			userRepository.save(user);
			System.out.println("Default user " + i + " created");
		}
	}
}
