package com.rick_morty.rick_morty_web_api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.exception.UserNotFoundException;
import com.rick_morty.rick_morty_web_api.mapper.CharacterMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final RickAndMortyDbCataloger db;
    private final CharacterMapper mapper;

    @Transactional
    public void save(CharacterSummaryDto characterSummaryDto) {
        if (characterSummaryDto != null) {
            var character = mapper.dtoToEntity(characterSummaryDto);

            if (characterSummaryDto.origin() != null) {
                var origin = db.getLocations().findById(characterSummaryDto.origin().id());
                origin.ifPresent(character::setOrigin);
            }

            if (characterSummaryDto.currentLocation() != null) {
                var currentLocation = db.getLocations().findById(characterSummaryDto.currentLocation().id());
                currentLocation.ifPresent(character::setLocation);
            }

            db.getCharacters().save(character);
        }
    }

    public List<CharacterSummaryDto> getAll() {
        return mapper.entityListToDtoList(db.getCharacters().findAll());
    }

    public CharacterSummaryDto getCharacterById(Integer id) {
        var character = db.getCharacters().findById(id);

        if (character.isEmpty()) {
            throw new UserNotFoundException("Character not found");
        }

        return mapper.entityToDto(character.get());
    }

    public List<CharacterSummaryDto> getAllLikeName(String like) {
        return mapper.entityListToDtoList(db.getCharacters().findLike("%" + like + "%"));
    }

    @Transactional
    public void update(Integer id, CharacterSummaryDto characterSummaryDto) {
        var characterOptional = db.getCharacters().findById(id);
        if (characterOptional.isEmpty()) {
            throw new UserNotFoundException("Character not found");
        }
        var character = characterOptional.get();

        character.setName(characterSummaryDto.name());
        character.setStatus(characterSummaryDto.status());
        character.setSpecies(characterSummaryDto.species());
        character.setType(characterSummaryDto.type());
        character.setGender(characterSummaryDto.gender());
        character.setImageUrl(characterSummaryDto.imageUrl());

        var origin = characterSummaryDto.origin() != null
                ? db.getLocations().findById(characterSummaryDto.origin().id())
                : null;
        var currentLocation = characterSummaryDto.currentLocation() != null
                ? db.getLocations().findById(characterSummaryDto.currentLocation().id())
                : null;

        origin.ifPresent(character::setOrigin);
        currentLocation.ifPresent(character::setLocation);

        db.getCharacters().save(character);
    }

    @Transactional
    public void deleteById(int id) {
        Character character = db.getCharacters().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Character not found"));
        for (Episode episode : character.getEpisodes()) {
            episode.getCharacters().remove(character);
            db.getEpisodes().save(episode); //update
        }
        db.getCharacters().delete(character);
    }
}
