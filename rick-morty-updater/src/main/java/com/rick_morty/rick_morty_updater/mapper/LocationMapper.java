package com.rick_morty.rick_morty_updater.mapper;

import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data_client.contract.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper implements DtoEntityMapper<LocationDto, Location> {
    @Override
    public Location mapDtoToEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setId(locationDto.id());
        location.setName(locationDto.name());
        location.setType(locationDto.type());
        location.setDimension(locationDto.dimension());
        location.setCreated(locationDto.created());
       // ?? location.setOriginCharacters(location.getOriginCharacters());
        // ?? location.setCurrentCharacters(location.getCurrentCharacters());
        return location;
    }
}