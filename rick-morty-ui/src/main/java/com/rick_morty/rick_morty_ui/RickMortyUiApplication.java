package com.rick_morty.rick_morty_ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.rick_morty")
public class RickMortyUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickMortyUiApplication.class, args);
	}

}
