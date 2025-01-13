package com.rick_morty.rick_morty_ui.service.uri_builder;

import org.springframework.web.util.UriComponentsBuilder;

public interface MyAppUriProvider {
    String host();

    String port();

    default UriComponentsBuilder builder(){
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(host())
                .port(port())
                .pathSegment("api");
    }
}
