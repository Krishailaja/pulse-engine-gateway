package com.pulseengine.gateway;

import com.pulseengine.gateway.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	CommandLineRunner initPassword(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			userRepository.findByUsername("john_doe").ifPresent(user -> {
				user.setPassword(passwordEncoder.encode("password123"));
				userRepository.save(user);
				System.out.println(">>> RESET SUCCESS: Updated password for john_doe in DB!");
			});
		};
	}
}

