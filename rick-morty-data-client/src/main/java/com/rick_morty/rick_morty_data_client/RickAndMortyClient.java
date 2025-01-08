package com.rick_morty.rick_morty_data_client;

import com.rick_morty.rick_morty_data_client.contract.CharactersDto;
import com.rick_morty.rick_morty_data_client.contract.EpisodesDto;
import com.rick_morty.rick_morty_data_client.contract.LocationsDto;
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
}
