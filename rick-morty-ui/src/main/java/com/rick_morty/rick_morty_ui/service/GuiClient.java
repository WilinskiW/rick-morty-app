package com.rick_morty.rick_morty_ui.service;

import com.rick_morty.rick_morty_ui.dto.EpisodeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GuiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${rick-morty.api.url}")
    private String apiUrl;

    public EpisodeDto getEpisode(String path) {
        String url = UriComponentsBuilder.fromUriString(apiUrl)
                .path(path)
                .toUriString();

        return restTemplate.getForObject(url, EpisodeDto.class);
    }
}
