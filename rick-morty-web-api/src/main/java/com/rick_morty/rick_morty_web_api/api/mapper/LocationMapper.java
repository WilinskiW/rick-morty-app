package com.rick_morty.rick_morty_web_api.api.mapper;

import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LocationMapper implements Mapper<Location, LocationDto> {
    @Override
    public LocationDto entityToDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getName(),
                location.getType(),
                location.getDimension(),
                residentsToDto(location));
    }

    private List<CharacterSummaryDto> residentsToDto(Location location) {
        return location.getCurrentCharacters().stream()
                .map(character -> new CharacterSummaryDto(character.getId(),character.getName(),
                        character.getStatus(), character.getSpecies(), character.getType(), character.getGender(),
                        locationSummaryDto(character.getOrigin()),
                        locationSummaryDto(character.getLocation()),
                        character.getImageUrl()))
                .toList();
    }

    private LocationSummaryDto locationSummaryDto(Location location) {
        if(location == null) {
            return null;
        }
        return new LocationSummaryDto(location.getId(), location.getName(), location.getType(), location.getDimension());
    }

    @Override
    public Location dtoToEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setSourceId(locationDto.id());
        location.setName(locationDto.name());
        location.setType(locationDto.type());
        location.setDimension(locationDto.dimension());
        location.setCreated(LocalDateTime.now());
        return location;
    }
}
