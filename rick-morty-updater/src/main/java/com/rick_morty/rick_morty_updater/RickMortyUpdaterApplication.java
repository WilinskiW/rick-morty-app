package com.rick_morty.rick_morty_updater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.rick_morty")
@EnableJpaRepositories(basePackages = "com.rick_morty")
@EnableAsync
public class RickMortyUpdaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickMortyUpdaterApplication.class, args);
	}

}
