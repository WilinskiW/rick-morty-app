package com.rick_morty.rick_morty_updater.mapper;

import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data_client.contract.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper implements DtoEntityMapper<LocationDto, Location> {
    @Override
    public Location mapDtoToEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setId(location.getId());
        location.setName(location.getName());
        location.setType(location.getType());
        location.setDimension(location.getDimension());
        location.setCreated(location.getCreated());
        location.setOriginCharacters(location.getOriginCharacters());
        location.setCurrentCharacters(location.getCurrentCharacters());
        return location;
    }
}