package com.rick_morty.rick_morty_data_client;

import com.rick_morty.rick_morty_data_client.contract.*;

public interface RickAndMortyDataPuller {
    ResultCharactersDto getCharacters();

    ResultCharactersDto getCharacters(int page);

    CharacterDto getCharacter(int id);

    ResultEpisodesDto getEpisodes();

    ResultEpisodesDto getEpisodes(int page);

    EpisodeDto getEpisode(int id);

    ResultLocationDto getLocations();

    ResultLocationDto getLocations(int page);

    LocationDto getLocation(int id);
}
