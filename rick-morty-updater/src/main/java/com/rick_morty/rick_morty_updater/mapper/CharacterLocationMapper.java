package com.rick_morty.rick_morty_updater.mapper;

import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data_client.contract.CharacterDto;
import com.rick_morty.rick_morty_data_client.contract.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class CharacterLocationMapper implements DtoEntityMapper<CharacterDto.LocationDto, Location>{
    private final LocationMapper locationMapper;

    public CharacterLocationMapper(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    @Override
    public Location mapDtoToEntity(CharacterDto.LocationDto characterLocationDto) {
        return null; //todo
    }
}
