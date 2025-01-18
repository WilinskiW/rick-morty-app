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

    public EpisodeDto getEpisode(String path, int id) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(id + "")
                .toUriString();

        try {
            return restTemplate.getForObject(url, EpisodeDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch episode", e);
        }
    }

    public List<EpisodeDto> getAllEpisodes() {
        String url = provider.builder()
                .pathSegment("episode")
                .pathSegment("all")
                .toUriString();

        ResponseEntity<List<EpisodeDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
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

    public CharacterDto getCharacter(String path, int id) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(id + "")
                .toUriString();

        try {
            return restTemplate.getForObject(url, CharacterDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch character", e);
        }
    }

    public List<LocationDto> getAllLocations() {
        String url = provider.builder()
                .pathSegment("location")
                .pathSegment("all")
                .toUriString();

        ResponseEntity<List<LocationDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public LocationDto getLocation(String path, int id) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(id + "")
                .toUriString();

        try {
            return restTemplate.getForObject(url, LocationDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch location", e);
        }
    }

    public void delete(String path, int id) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(id + "")
                .toUriString();

        restTemplate.delete(url);
    }

    public <T> void update(String path, int id, T body) {
        String url = provider.builder()
                .pathSegment(path)
                .pathSegment(id + "")
                .toUriString();

        restTemplate.put(url, body, id);
    }

    public CharacterDto.LocationSummaryDto getLocationSummary(int id) {
        var location = getLocation("location", id);
        CharacterDto.LocationSummaryDto locationSummary = new CharacterDto.LocationSummaryDto();
        locationSummary.setId(location.getId());
        locationSummary.setName(location.getName());
        locationSummary.setType(location.getType());
        locationSummary.setDimension(location.getDimension());
        return locationSummary;
    }
}
