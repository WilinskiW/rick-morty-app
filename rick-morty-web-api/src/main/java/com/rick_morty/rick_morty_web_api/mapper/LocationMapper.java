package com.rick_morty.rick_morty_web_api.mapper;

import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.contract.LocationDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<LocationDto.Residents> residentsToDto(Location location) {
        return location.getCurrentCharacters().stream()
                .map(c -> new LocationDto.Residents(c.getSourceId(), c.getName()))
                .toList();
    }

    @Override
    public List<LocationDto> entityListToDtoList(List<Location> locations) {
        return locations.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
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
