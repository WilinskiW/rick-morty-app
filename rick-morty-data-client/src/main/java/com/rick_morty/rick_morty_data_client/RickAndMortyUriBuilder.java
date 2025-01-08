package com.rick_morty.rick_morty_data_client;

import org.springframework.beans.factory.annotation.Value;

public record RickAndMortyUriBuilder(String host) implements RickAndMortyUriBuilderProvider{
    public RickAndMortyUriBuilder(@Value("${rick-morty.host}") String host) {
        this.host = host;
    }
}
