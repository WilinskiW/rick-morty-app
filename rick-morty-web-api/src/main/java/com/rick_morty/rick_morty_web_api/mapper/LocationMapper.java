package com.rick_morty.rick_morty_web_api.mapper;

import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.contract.LocationSummaryDto;
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
                .map(c -> new CharacterSummaryDto(
                        c.getId(),
                        c.getName(),
                        c.getStatus(),
                        c.getSpecies(),
                        c.getType(),
                        c.getGender(),
                        summaryToDto(location),
                        summaryToDto(location),
                        c.getImageUrl()
                ))
                .toList();
    }

    private LocationSummaryDto summaryToDto(Location location) {
        return new LocationSummaryDto(location.getId(),
                location.getName(),
                location.getType(),
                location.getDimension());
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
