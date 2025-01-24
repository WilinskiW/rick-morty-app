package com.rick_morty.rick_morty_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.rick_morty.rick_morty_data.model")
@EnableJpaRepositories(basePackages = "com.rick_morty.rick_morty_data.repository.security")
public class RickMortySecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickMortySecurityApplication.class, args);
	}

}
