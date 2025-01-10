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
    public ResultCharactersDto getCharacters() {
        var uri = provider.builder()
                .pathSegment("character")
                .toUriString();
        return restClient.getForEntity(uri, ResultCharactersDto.class).getBody();
    }

    @Override
    public ResultCharactersDto getCharacters(int page) {
        var uri = provider.builder()
                .pathSegment("character")
                .queryParam("page", page)
                .toUriString();
        return restClient.getForEntity(uri, ResultCharactersDto.class).getBody();
    }

    @Override
    public ResultEpisodesDto getEpisodes() {
        var uri = provider.builder()
                .pathSegment("episode")
                .toUriString();
        return restClient.getForEntity(uri, ResultEpisodesDto.class).getBody();
    }

    @Override
    public ResultEpisodesDto getEpisodes(int page) {
        var uri = provider.builder()
                .pathSegment("episode")
                .queryParam("page", page)
                .toUriString();
        return restClient.getForEntity(uri, ResultEpisodesDto.class).getBody();
    }

    @Override
    public ResultLocationDto getLocations() {
        var uri = provider.builder()
                .pathSegment("location")
                .toUriString();
        return restClient.getForEntity(uri, ResultLocationDto.class).getBody();
    }

    @Override
    public ResultLocationDto getLocations(int page) {
        var uri = provider.builder()
                .pathSegment("location")
                .queryParam("page", page)
                .toUriString();
        return restClient.getForEntity(uri, ResultLocationDto.class).getBody();
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
