package com.rick_morty.rick_morty_web_api.service;

import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.exception.UserNotFoundException;
import com.rick_morty.rick_morty_web_api.mapper.CharacterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final RickAndMortyDbCataloger db;
    private final CharacterMapper characterMapper;

    public List<CharacterSummaryDto> getAll() {
        return db.getCharacters()
                .findAll()
                .stream()
                .map(characterMapper::entityToDto)
                .toList();
    }

    public CharacterSummaryDto getCharacterById(Integer id) {
        var character = db.getCharacters().findById(id);
        if(character.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return characterMapper.entityToDto(character.get());
    }
}
