package com.rick_morty.rick_morty_updater.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data_client.contract.CharacterDto;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper implements DtoEntityMapper<CharacterDto, Character> {
    @Override
    public Character mapDtoToEntity(CharacterDto characterDto) {
        Character character = new Character();
        character.setId(character.getId());
        character.setName(character.getName());
        character.setStatus(character.getStatus());
        character.setSpecies(character.getSpecies());
        character.setType(character.getType());
        character.setGender(character.getGender());
        character.setOrigin(character.getOrigin());
        character.setLocation(character.getLocation());
        character.setImageUrl(character.getImageUrl());
        character.setCreated(character.getCreated());
        return character;
    }
}
