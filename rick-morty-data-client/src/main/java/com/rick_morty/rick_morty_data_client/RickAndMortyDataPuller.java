package com.rick_morty.rick_morty_data_client;

import com.rick_morty.rick_morty_data_client.contract.CharactersDto;
import com.rick_morty.rick_morty_data_client.contract.EpisodesDto;
import com.rick_morty.rick_morty_data_client.contract.LocationsDto;

public interface RickAndMortyDataPuller {
    CharactersDto getCharacters();
    EpisodesDto getEpisodes();
    LocationsDto getLocations();
}
