package com.rick_morty.rick_morty_data_client;

import com.rick_morty.rick_morty_data_client.contract.*;

import java.util.List;

public interface RickAndMortyDataPuller {
    List<CharacterDto> getCharacters();

    ResultCharactersDto getCharactersPage(int page);

    CharacterDto getCharacter(int id);

    List<EpisodeDto> getEpisodes();

    ResultEpisodesDto getEpisodesPage(int page);

    EpisodeDto getEpisode(int id);

    List<LocationDto> getLocations();

    ResultLocationDto getLocationsPage(int page);

    LocationDto getLocation(int id);
}
