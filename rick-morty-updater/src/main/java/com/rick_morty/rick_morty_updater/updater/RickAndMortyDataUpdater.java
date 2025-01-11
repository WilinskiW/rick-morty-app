package com.rick_morty.rick_morty_updater.updater;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_data_client.RickAndMortyDataPuller;
import com.rick_morty.rick_morty_data_client.contract.*;
import com.rick_morty.rick_morty_updater.mapper.DtoToEntityMapper;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RickAndMortyDataUpdater {
    private final RickAndMortyDataPuller apiClient;
    private final RickAndMortyDbCataloger dbCataloger;
    private final DtoToEntityMapper mapper;

    public RickAndMortyDataUpdater(RickAndMortyDataPuller apiClient, RickAndMortyDbCataloger dbCataloger, DtoToEntityMapper mapper) {
        this.apiClient = apiClient;
        this.dbCataloger = dbCataloger;
        this.mapper = mapper;
    }

    @Transactional
    @Async
    public void syncData() {
        Map<Integer, Location> locationMap = fetchAllLocations();

        System.out.println("Fetched " + locationMap.size() + " locations");

        Map<Integer, Episode> episodeMap = fetchAllEpisodes();

        System.out.println("Fetched " + episodeMap.size() + " episodes");

        fetchAllCharacters(locationMap, episodeMap);
    }

    private Map<Integer, Location> fetchAllLocations() {
        List<LocationDto> locationsDto = apiClient.getLocations();
        return locationsDto.stream()
                .map(mapper::mapToEntity)
                .peek(location -> {
                    boolean exists = Boolean.TRUE.equals(dbCataloger.getLocations().existsBySourceId(location.getSourceId()));
                    if (!exists) {
                        dbCataloger.getLocations().save(location);
                    }
                })
                .collect(Collectors.toMap(Location::getSourceId, loc -> loc));
    }


    private Map<Integer, Episode> fetchAllEpisodes() {
        List<EpisodeDto> episodesDto = apiClient.getEpisodes();
        return episodesDto.stream()
                .map(mapper::mapToEntity)
                .peek(episode -> {
                    boolean exists = Boolean.TRUE.equals(dbCataloger.getEpisodes().existsBySourceId(episode.getSourceId()));
                    if (!exists) {
                        dbCataloger.getEpisodes().save(episode);
                    }
                })
                .collect(Collectors.toMap(Episode::getSourceId, ep -> ep));
    }


    private void fetchAllCharacters(Map<Integer, Location> locationMap, Map<Integer, Episode> episodeMap) {
        int page = 1;
        int maxPage = 0;
        do {
            ResultCharactersDto charactersDto = apiClient.getCharactersPage(page);
            charactersDto.results().forEach(characterDto -> {
                Location origin = getCharacterLocation(characterDto.origin(), locationMap);
                Location currentLocation = getCharacterLocation(characterDto.location(), locationMap);
                Set<Episode> episodes = characterDto.episode().stream()
                        .map(this::extractIdFromUrl)
                        .map(episodeMap::get)
                        .collect(Collectors.toSet());

                Character character = mapper.mapToEntity(characterDto, origin, currentLocation, episodes);

                boolean exists = Boolean.TRUE.equals(dbCataloger.getCharacters().existsBySourceId(character.getSourceId()));
                if (!exists) {
                    dbCataloger.getCharacters().save(character);
                }
            });

            if (page == 1) {
                maxPage = charactersDto.info().pages();
            }

            page++;
        } while (page <= maxPage);
    }


    private Location getCharacterLocation(CharacterDto.LocationDto loc, Map<Integer, Location> locationMap) {
        if(!loc.url().isEmpty()){
            return locationMap.get(extractIdFromUrl(loc.url()));
        }
        return null;
    }

    private int extractIdFromUrl(String url) {
        String[] parts = url.split("/");

        return Integer.parseInt(parts[parts.length - 1]);
    }
}