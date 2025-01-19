package com.rick_morty.rick_morty_web_api.api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import com.rick_morty.rick_morty_web_api.api.mapper.LocationMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final RickAndMortyDbCataloger db;
    private final LocationMapper mapper;

    @Transactional
    public void save(LocationDto locationDto) {
        if (locationDto != null) {
            Location location = new Location();

            Set<Character> characters = locationDto.residents().stream()
                    .map(dto -> {
                        var character = db.getCharacters().findById(dto.id())
                                .orElseThrow(() -> new EntityNotFoundException("Character not found"));
                       character.setLocation(location);
                       return character;
                    })
                    .collect(Collectors.toSet());

            location.setName(locationDto.name());
            location.setType(locationDto.type());
            location.setDimension(locationDto.dimension());
            location.setCreated(LocalDateTime.now());
            location.setCurrentCharacters(characters);

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

        location.getOriginCharacters().forEach(originCharacter -> originCharacter.setOrigin(null));
        location.getCurrentCharacters().forEach(currentCharacter -> currentCharacter.setLocation(null));

        db.getLocations().delete(location);
    }

    @Transactional
    public void removeCharacterFromLocation(Integer locationId, Integer characterId) {
        Location location = db.getLocations().findById(locationId).orElseThrow(() -> new RuntimeException("Location not found"));
        Character character = db.getCharacters().findById(characterId).orElseThrow(() -> new RuntimeException("Character not found"));

        location.getCurrentCharacters().remove(character);

        character.setLocation(null);

        db.getLocations().save(location);
        db.getCharacters().save(character);
    }

}
