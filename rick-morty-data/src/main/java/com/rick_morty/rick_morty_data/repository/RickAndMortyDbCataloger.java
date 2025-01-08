package com.rick_morty.rick_morty_data.repository;

public interface RickAndMortyDbCataloger {
    AuthorityRepository getAuthorityRepository();
    UserRepository getUserRepository();
    UserFavoritesRepository getUserFavoritesRepository();
    EpisodeRepository getEpisodeRepository();
    LocationRepository getLocationRepository();
    CharacterRepository getCharacterRepository();
}
