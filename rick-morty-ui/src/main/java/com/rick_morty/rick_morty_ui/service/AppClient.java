package com.rick_morty.rick_morty_ui.service;

import com.rick_morty.rick_morty_ui.dto.CharacterDto;
import com.rick_morty.rick_morty_ui.dto.EpisodeDto;
import com.rick_morty.rick_morty_ui.service.uri_builder.MyAppUriProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final MyAppUriProvider provider;

    public EpisodeDto getEpisode(String path, int id) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(id+"")
                .toUriString();

        System.out.println("Constructed URL: " + url);

        try {
            return restTemplate.getForObject(url, EpisodeDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch episode", e);
        }
    }


    public List<CharacterDto> getAllCharacters() {
        String url = provider.builder()
                .pathSegment("character")
                .pathSegment("all")
                .toUriString();

        ResponseEntity<List<CharacterDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }
}
