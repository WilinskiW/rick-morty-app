package com.rick_morty.rick_morty_ui.service;

import com.rick_morty.rick_morty_ui.dto.CharacterDto;
import com.rick_morty.rick_morty_ui.dto.EpisodeDto;
import com.rick_morty.rick_morty_ui.dto.LocationDto;
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

    private <T> T getForObject(String path, Class<T> responseType, String... pathVariables) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(pathVariables)
                .toUriString();

        try {
            return restTemplate.getForObject(url, responseType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch data", e);
        }
    }

    private <T> List<T> getAllForObject(String path, Class<T> responseType, String... pathVariables) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(pathVariables)
                .toUriString();

        ResponseEntity<List<T>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public <T> void update(String path, int id, T body) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(String.valueOf(id))
                .toUriString();

        restTemplate.put(url, body, id);
    }

    public void delete(String path, int id) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(String.valueOf(id))
                .toUriString();

        restTemplate.delete(url);
    }

    public EpisodeDto getEpisode(int id) {
        return getForObject("episode", EpisodeDto.class, String.valueOf(id));
    }

    public List<EpisodeDto> getAllEpisodes() {
        return getAllForObject("episode", EpisodeDto.class, "all");
    }

    public List<CharacterDto> getAllCharacters() {
        return getAllForObject("character", CharacterDto.class, "all");
    }

    public CharacterDto getCharacter(int id) {
        return getForObject("character", CharacterDto.class, String.valueOf(id));
    }

    public List<LocationDto> getAllLocations() {
        return getAllForObject("location", LocationDto.class, "all");
    }

    public LocationDto getLocation(int id) {
        return getForObject("location", LocationDto.class, String.valueOf(id));
    }

    public CharacterDto.LocationSummaryDto getLocationSummary(int id) {
        LocationDto location = getLocation(id);
        CharacterDto.LocationSummaryDto locationSummary = new CharacterDto.LocationSummaryDto();
        locationSummary.setId(location.getId());
        locationSummary.setName(location.getName());
        locationSummary.setType(location.getType());
        locationSummary.setDimension(location.getDimension());
        return locationSummary;
    }
}
