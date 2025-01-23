package com.rick_morty.rick_morty_web_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.rick_morty")
@EnableJpaRepositories(basePackages = "com.rick_morty.rick_morty_data")
@EnableScheduling
public class RickMortyWebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickMortyWebApiApplication.class, args);
	}

}
