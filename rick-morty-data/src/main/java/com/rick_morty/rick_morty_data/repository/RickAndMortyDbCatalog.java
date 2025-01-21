package com.rick_morty.rick_morty_data.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RickAndMortyDbCatalog implements RickAndMortyDbCataloger{
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;
    private final CharacterRepository characterRepository;
    private final UserFavoritesRepository userFavoritesRepository;
    private final LocationRepository locationRepository;

    @Override
    public UserRepository getUsers() {
        return userRepository;
    }

    @Override
    public UserFavoritesRepository getUserFavoritesRepository() {
        return userFavoritesRepository;
    }

    @Override
    public EpisodeRepository getEpisodes() {
        return episodeRepository;
    }

    @Override
    public LocationRepository getLocations() {
        return locationRepository;
    }

    @Override
    public CharacterRepository getCharacters() {
        return characterRepository;
    }
}
