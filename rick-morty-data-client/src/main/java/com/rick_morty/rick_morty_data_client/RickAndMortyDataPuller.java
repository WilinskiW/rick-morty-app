package com.rick_morty.rick_morty_data_client;

import com.rick_morty.rick_morty_data_client.contract.*;

public interface RickAndMortyDataPuller {
    CharactersDto getCharacters();

    EpisodesDto getEpisodes();

    LocationsDto getLocations();

    CharacterDto getCharacter(int id);

    EpisodeDto getEpisode(int id);

    LocationDto getLocation(int id);


}
