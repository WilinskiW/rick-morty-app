package com.rick_morty.rick_morty_data_client;

import org.springframework.web.util.UriComponentsBuilder;

public interface RickAndMortyUriBuilderProvider {
    String host();

    default UriComponentsBuilder builder(){
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host())
                .pathSegment("api");
    }
}
