package com.rick_morty.rick_morty_updater.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data_client.contract.CharacterDto;
import com.rick_morty.rick_morty_data_client.contract.EpisodeDto;
import com.rick_morty.rick_morty_data_client.contract.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper implements CatalogMapper{
    private final DtoEntityMapper<CharacterDto, Character> characterMapper;
    private final DtoEntityMapper<EpisodeDto, Episode> episodeMapper;
    private final DtoEntityMapper<LocationDto, Location> locationMapper;

    public EntityMapper(DtoEntityMapper<CharacterDto, Character> characterMapper, DtoEntityMapper<EpisodeDto, Episode> episodeMapper, DtoEntityMapper<LocationDto, Location> locationMapper) {
        this.characterMapper = characterMapper;
        this.episodeMapper = episodeMapper;
        this.locationMapper = locationMapper;
    }

    @Override
    public DtoEntityMapper<CharacterDto, Character> forCharacter() {
        return characterMapper;
    }

    @Override
    public DtoEntityMapper<LocationDto, Location> forLocation() {
        return locationMapper;
    }

    @Override
    public DtoEntityMapper<EpisodeDto, Episode> forEpisode() {
        return episodeMapper;
    }
}
