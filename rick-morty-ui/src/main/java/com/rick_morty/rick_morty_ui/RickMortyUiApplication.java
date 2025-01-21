package com.rick_morty.rick_morty_ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.rick_morty")
@EnableJpaRepositories(basePackages = "com.rick_morty.rick_morty_data")
public class RickMortyUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickMortyUiApplication.class, args);
	}

}
