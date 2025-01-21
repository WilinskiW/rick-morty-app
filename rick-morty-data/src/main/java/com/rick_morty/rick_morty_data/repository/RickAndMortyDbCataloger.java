package com.rick_morty.rick_morty_data.repository;

public interface RickAndMortyDbCataloger {
    UserRepository getUsers();
    UserFavoritesRepository getUserFavoritesRepository();
    EpisodeRepository getEpisodes();
    LocationRepository getLocations();
    CharacterRepository getCharacters();
}
