package com.rick_morty.rick_morty_data_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RickAndMortyClientConfig {

    @Bean
    public RickAndMortyUriBuilderProvider MoviesClientUriBuilderProvider(
            @Value("${rick-morty.host}") String host) {
        return new RickAndMortyUriBuilder(host);
    }

    @Bean
    @Scope("prototype")
    public RickAndMortyClient moviesClient(RickAndMortyUriBuilderProvider uriBuilderProvider) {
        return new RickAndMortyClient(uriBuilderProvider);
    }
}
