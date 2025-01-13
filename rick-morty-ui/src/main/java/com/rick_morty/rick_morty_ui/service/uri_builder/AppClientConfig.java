package com.rick_morty.rick_morty_ui.service.uri_builder;

import com.rick_morty.rick_morty_ui.service.AppClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppClientConfig {

    @Bean
    public MyAppUriProvider AppClientBuilderProvider(
            @Value("${webapi.host}") String host,
            @Value("${webapi.port}") String port) {
        return new MyAppUri(host, port);
    }

    @Bean
    @Scope("prototype")
    public AppClient myAppClient(MyAppUriProvider uriBuilderProvider) {
        return new AppClient(uriBuilderProvider);
    }
}
