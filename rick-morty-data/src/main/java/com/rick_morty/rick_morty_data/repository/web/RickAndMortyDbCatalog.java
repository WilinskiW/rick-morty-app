package com.rick_morty.rick_morty_data.repository.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RickAndMortyDbCatalog implements RickAndMortyDbCataloger{
    private final EpisodeRepository episodeRepository;
    private final CharacterRepository characterRepository;
    private final LocationRepository locationRepository;

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
