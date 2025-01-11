package com.rick_morty.rick_morty_web_api.service;

import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.contract.CreateLocationDto;
import com.rick_morty.rick_morty_web_api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.contract.LocationSummaryDto;
import com.rick_morty.rick_morty_web_api.mapper.LocationMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final RickAndMortyDbCataloger db;
    private final LocationMapper mapper;

    @Transactional
    public void save(CreateLocationDto createLocationDto) {
        if (createLocationDto != null) {
            Location location = new Location();
            location.setName(createLocationDto.name());
            location.setType(createLocationDto.type());
            location.setDimension(createLocationDto.dimension());
            location.setCreated(LocalDateTime.now());

            db.getLocations().save(location);
        }
    }

    public List<LocationDto> getAll() {
        return mapper.entityListToDtoList(db.getLocations().findAll());
    }

    public LocationDto getById(Integer id) {
        var location = db.getLocations().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        return mapper.entityToDto(location);
    }

    @Transactional
    public void update(Integer id, LocationSummaryDto locationSummaryDto) {
        var locationOptional = db.getLocations().findById(id);
        if (locationOptional.isEmpty()) {
            throw new EntityNotFoundException("Location not found");
        }
        var location = locationOptional.get();

        location.setName(locationSummaryDto.name());
        location.setType(locationSummaryDto.type());
        location.setDimension(locationSummaryDto.dimension());

        db.getLocations().save(location);
    }

    @Transactional
    public void deleteById(Integer id) {
        var location = db.getLocations().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        db.getLocations().delete(location);
    }
}
