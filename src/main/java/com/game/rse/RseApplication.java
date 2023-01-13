package com.game.rse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableJpaAuditing
@SpringBootApplication
public class RseApplication {
	public static void main(String[] args) {
		SpringApplication.run(RseApplication.class, args);
	}
}