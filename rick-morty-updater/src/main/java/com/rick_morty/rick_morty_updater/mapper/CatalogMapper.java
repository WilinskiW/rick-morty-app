package com.rick_morty.rick_morty_updater.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data_client.contract.CharacterDto;
import com.rick_morty.rick_morty_data_client.contract.EpisodeDto;
import com.rick_morty.rick_morty_data_client.contract.LocationDto;

public interface CatalogMapper {
    DtoEntityMapper <CharacterDto, Character> forCharacter();

    DtoEntityMapper <LocationDto, Location> forLocation();

    DtoEntityMapper <EpisodeDto, Episode> forEpisode();

}
