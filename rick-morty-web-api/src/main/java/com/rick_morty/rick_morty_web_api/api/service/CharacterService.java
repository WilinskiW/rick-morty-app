package com.rick_morty.rick_morty_web_api.api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.repository.web.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.exception.DataNotFoundException;
import com.rick_morty.rick_morty_web_api.api.mapper.CharacterMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final RickAndMortyDbCataloger db;
    private final CharacterMapper mapper;

    @Transactional
    public void save(CharacterDto characterDto) {
        if (db.getCharacters().existsByName(characterDto.getName())) {
            throw new EntityExistsException("Character with this name already exists");
        }

        var character = mapper.dtoToEntity(characterDto);

        if (characterDto.getOrigin() != null) {
            var origin = db.getLocations().findById(characterDto.getOrigin().getId());
            origin.ifPresent(character::setOrigin);
        }

        if (characterDto.getCurrentLocation() != null) {
            var currentLocation = db.getLocations().findById(characterDto.getCurrentLocation().getId());
            currentLocation.ifPresent(character::setLocation);
        }

        db.getCharacters().save(character);
    }

    public Page<CharacterDto> getAll(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 25);
        return mapper.entityListToDtoPage(db.getCharacters().findAll(pageable));
    }

    public List<CharacterDto> getAllNotInTheEpisode(int episodeId) {
        return mapper.entityListToDtoList(db.getCharacters().findByEpisodeNotIn(episodeId));
    }

    public List<CharacterDto> getAllNotInTheLocation(int locationId) {
        return mapper.entityListToDtoList(db.getCharacters().findByLocationNotIn(locationId));
    }

    public CharacterDto getCharacterById(Integer id) {
        var character = db.getCharacters().findById(id);

        if (character.isEmpty()) {
            throw new DataNotFoundException("Character not found");
        }

        return mapper.entityToDto(character.get());
    }

    @Transactional
    public void update(Integer id, CharacterDto characterDto) {
        var characterOptional = db.getCharacters().findById(id);
        if (characterOptional.isEmpty()) {
            throw new DataNotFoundException("Character not found");
        }
        var character = characterOptional.get();

        character.setName(characterDto.getName());
        character.setStatus(characterDto.getStatus());
        character.setSpecies(characterDto.getSpecies());
        character.setType(characterDto.getType());
        character.setGender(characterDto.getGender());
        character.setImageUrl(characterDto.getImageUrl());

        var origin = characterDto.getOrigin() != null
                ? db.getLocations().findById(characterDto.getOrigin().getId())
                : null;
        var currentLocation = characterDto.getCurrentLocation() != null
                ? db.getLocations().findById(characterDto.getCurrentLocation().getId())
                : null;

        origin.ifPresent(character::setOrigin);
        currentLocation.ifPresent(character::setLocation);

        db.getCharacters().save(character);
    }

    @Transactional
    public void deleteById(int id) {
        Character character = db.getCharacters().findById(id)
                .orElseThrow(() -> new DataNotFoundException("Character not found"));
        for (Episode episode : character.getEpisodes()) {
            episode.getCharacters().remove(character);
            db.getEpisodes().save(episode); //update
        }
        db.getCharacters().delete(character);
    }
}
