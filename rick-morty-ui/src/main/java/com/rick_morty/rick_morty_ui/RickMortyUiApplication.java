package com.rick_morty.rick_morty_ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages =
        {
                "com.rick_morty.rick_morty_web_api",
                " com.rick_morty.rick_morty_security"
        }
)
public class RickMortyUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RickMortyUiApplication.class, args);
    }

}
