package com.rick_morty.rick_morty_data.repository.web;

public interface RickAndMortyDbCataloger {
    EpisodeRepository getEpisodes();
    LocationRepository getLocations();
    CharacterRepository getCharacters();
}
