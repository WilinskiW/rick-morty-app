package com.rick_morty.rick_morty_data.repository;

import org.springframework.stereotype.Repository;

@Repository
public class RickAndMortyDbCatalog implements RickAndMortyDbCataloger{
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;
    private final CharacterRepository characterRepository;
    private final UserFavoritesRepository userFavoritesRepository;
    private final LocationRepository locationRepository;


    public RickAndMortyDbCatalog(AuthorityRepository authorityRepository, UserRepository userRepository, EpisodeRepository episodeRepository, CharacterRepository characterRepository, UserFavoritesRepository userFavoritesRepository, LocationRepository locationRepository) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.episodeRepository = episodeRepository;
        this.characterRepository = characterRepository;
        this.userFavoritesRepository = userFavoritesRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public AuthorityRepository getAuthorities() {
        return authorityRepository;
    }

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
