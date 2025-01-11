package com.rick_morty.rick_morty_data_client;

import com.rick_morty.rick_morty_data_client.contract.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RickAndMortyClient implements RickAndMortyDataPuller {
    private final RestTemplate restClient;
    private final RickAndMortyUriBuilderProvider provider;

    public RickAndMortyClient(RickAndMortyUriBuilderProvider provider) {
        this.restClient = new RestTemplate();
        this.provider = provider;
    }

    @Override
    public List<CharacterDto> getCharacters() {
        return fetchAll("character",
                ResultCharactersDto.class,
                ResultCharactersDto::results,
                result -> result.info().pages());
    }

    @Override
    public ResultCharactersDto getCharactersPage(int page) {
        return fetchSinglePage("character", page, ResultCharactersDto.class);
    }

    @Override
    public CharacterDto getCharacter(int id) {
        return fetchById("character", id, CharacterDto.class);
    }

    @Override
    public List<EpisodeDto> getEpisodes() {
        return fetchAll("episode",
                ResultEpisodesDto.class,
                ResultEpisodesDto::results,
                result -> result.info().pages());
    }

    @Override
    public ResultEpisodesDto getEpisodesPage(int page) {
        return fetchSinglePage("episode", page, ResultEpisodesDto.class);
    }

    @Override
    public EpisodeDto getEpisode(int id) {
        return fetchById("episode", id, EpisodeDto.class);
    }

    @Override
    public List<LocationDto> getLocations() {
        return fetchAll("location",
                ResultLocationDto.class,
                ResultLocationDto::results,
                result -> result.info().pages());
    }

    @Override
    public ResultLocationDto getLocationsPage(int page) {
        return fetchSinglePage("location", page, ResultLocationDto.class);
    }


    @Override
    public LocationDto getLocation(int id) {
        return fetchById("location", id, LocationDto.class);
    }

    private <T, R> List<R> fetchAll(String path, Class<T> responseType, Function<T, List<R>> extractor, Function<T, Integer> pageCounter) {
        var firstPage = fetchSinglePage(path, 1, responseType);
        List<R> results = new ArrayList<>(extractor.apply(firstPage));
        int totalPages = pageCounter.apply(firstPage);

        for (int page = 2; page <= totalPages; page++) {
            T pageData = fetchSinglePage(path, page, responseType);
            results.addAll(extractor.apply(pageData));
        }
        return results;
    }

    private <T> T fetchSinglePage(String path, int pages, Class<T> responseType) {
        var uri = provider.builder()
                .pathSegment(path)
                .queryParam("page", pages)
                .toUriString();

        var response = restClient.getForEntity(uri, responseType).getBody();

        if (response == null) {
            throw new RuntimeException("No response received from API: " + uri);
        }

        return response;
    }

    private <T> T fetchById(String path, int id, Class<T> responseType) {
        var uri = provider.builder()
                .pathSegment(path)
                .pathSegment("" + id)
                .toUriString();

        var response = restClient.getForEntity(uri, responseType).getBody();

        if (response == null) {
            throw new RuntimeException("No response received from API: " + uri);
        }

        return response;
    }
}
