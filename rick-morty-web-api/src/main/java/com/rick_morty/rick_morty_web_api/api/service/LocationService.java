package com.rick_morty.rick_morty_web_api.api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.web.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.exception.DataNotFoundException;
import com.rick_morty.rick_morty_web_api.api.mapper.LocationMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final RickAndMortyDbCataloger db;
    private final LocationMapper mapper;

    @Transactional
    public void save(LocationDto locationDto) {
        if (db.getLocations().existsByName(locationDto.getName())) {
            throw new EntityExistsException("Location with this name already exists");
        }

        Location location = new Location();

        Set<Character> characters = locationDto.getResidents().stream()
                .map(dto -> {
                    var character = db.getCharacters().findById(dto.getId())
                            .orElseThrow(() -> new DataNotFoundException("Character not found"));
                    character.setLocation(location);
                    return character;
                })
                .collect(Collectors.toSet());

        location.setName(locationDto.getName());
        location.setType(locationDto.getType());
        location.setDimension(locationDto.getDimension());
        location.setCreated(LocalDateTime.now());
        location.setCurrentCharacters(characters);

        db.getLocations().save(location);
    }

    public Page<LocationDto> getAll(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 25);
        return mapper.entityListToDtoPage(db.getLocations().findAll(pageable));
    }

    public LocationDto getById(Integer id) {
        var location = db.getLocations().findById(id)
                .orElseThrow(() -> new DataNotFoundException("Location not found"));
        return mapper.entityToDto(location);
    }

    @Transactional
    public void update(Integer id, LocationDto locationDto) {
        var locationOptional = db.getLocations().findById(id);
        if (locationOptional.isEmpty()) {
            throw new DataNotFoundException("Location not found");
        }
        var location = locationOptional.get();

        location.setName(locationDto.getName());
        location.setType(locationDto.getType());
        location.setDimension(locationDto.getDimension());
        if (locationDto.getResidents() != null) {
            locationDto.getResidents().forEach(dto -> {
                removeCharacterFromLocation(id, dto.getId());
                Character character = db.getCharacters().findById(dto.getId())
                        .orElseThrow(() -> new DataNotFoundException("Character not found"));
                character.setLocation(location);
                location.getCurrentCharacters().add(character);
            });
        }

        db.getLocations().save(location);
    }

    @Transactional
    public void deleteById(Integer id) {
        var location = db.getLocations().findById(id)
                .orElseThrow(() -> new DataNotFoundException("Location not found"));

        location.getOriginCharacters().forEach(originCharacter -> originCharacter.setOrigin(null));
        location.getCurrentCharacters().forEach(currentCharacter -> currentCharacter.setLocation(null));

        db.getLocations().delete(location);
    }

    @Transactional
    public void removeCharacterFromLocation(Integer locationId, Integer characterId) {
        Location location = db.getLocations().findById(locationId).orElseThrow(() -> new DataNotFoundException("Location not found"));
        Character character = db.getCharacters().findById(characterId).orElseThrow(() -> new DataNotFoundException("Character not found"));

        location.getCurrentCharacters().remove(character);

        character.setLocation(null);

        db.getLocations().save(location);
        db.getCharacters().save(character);
    }

}
