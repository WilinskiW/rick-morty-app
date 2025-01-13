package com.rick_morty.rick_morty_ui.service.uri_builder;

import org.springframework.beans.factory.annotation.Value;

public record MyAppUri(String host, String port) implements MyAppUriProvider {
    public MyAppUri(@Value("${webapi.host}") String host,
                    @Value("${webapi.port}") String port) {
        this.host = host;
        this.port = port;
    }
}
