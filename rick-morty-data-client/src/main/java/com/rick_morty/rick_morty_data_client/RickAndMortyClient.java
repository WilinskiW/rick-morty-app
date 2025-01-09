package com.rick_morty.rick_morty_data_client;

import com.rick_morty.rick_morty_data_client.contract.*;
import org.springframework.web.client.RestTemplate;

public class RickAndMortyClient implements RickAndMortyDataPuller {
    private final RestTemplate restClient;
    private final RickAndMortyUriBuilderProvider provider;

    public RickAndMortyClient(RickAndMortyUriBuilderProvider provider) {
        this.restClient = new RestTemplate();
        this.provider = provider;
    }

    @Override
    public CharactersDto getCharacters() {
        var uri = provider.builder()
                .pathSegment("character")
                .toUriString();
        return restClient.getForEntity(uri, CharactersDto.class).getBody();
    }

    @Override
    public EpisodesDto getEpisodes() {
        var uri = provider.builder()
                .pathSegment("episode")
                .toUriString();
        return restClient.getForEntity(uri, EpisodesDto.class).getBody();
    }

    @Override
    public LocationsDto getLocations() {
        var uri = provider.builder()
                .pathSegment("location")
                .toUriString();
        return restClient.getForEntity(uri, LocationsDto.class).getBody();
    }

    @Override
    public CharacterDto getCharacter(int id) {
        var uri = provider.builder()
                .pathSegment("character")
                .pathSegment("" + id)
                .toUriString();
        return restClient.getForEntity(uri, CharacterDto.class).getBody();
    }

    @Override
    public EpisodeDto getEpisode(int id) {
        var uri = provider.builder()
                .pathSegment("episode")
                .pathSegment("" + id)
                .toUriString();
        return restClient.getForEntity(uri, EpisodeDto.class).getBody();
    }

    @Override
    public LocationDto getLocation(int id) {
        var uri = provider.builder()
                .pathSegment("location")
                .pathSegment("" + id)
                .toUriString();
        return restClient.getForEntity(uri, LocationDto.class).getBody();
    }
}
