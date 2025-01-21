package com.rick_morty.rick_morty_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.rick_morty")
@EnableJpaRepositories("com.rick_morty.rick_morty_data")
public class RickMortySecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickMortySecurityApplication.class, args);
	}

}
