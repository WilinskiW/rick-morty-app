package com.rick_morty.rick_morty_updater.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data_client.contract.CharacterDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class CharacterMapper implements DtoEntityMapper<CharacterDto, Character> {
    private final CharacterLocationMapper characterLocationMapper;

    public CharacterMapper(CharacterLocationMapper characterLocationMapper) {
        this.characterLocationMapper = characterLocationMapper;
    }

    @Override
    public Character mapDtoToEntity(CharacterDto characterDto) {
        Character character = new Character();
        character.setSource_id(characterDto.id());
        character.setName(characterDto.name());
        character.setStatus(characterDto.status());
        character.setSpecies(characterDto.species());
        character.setType(characterDto.type());
        character.setGender(characterDto.gender());
        character.setOrigin(characterLocationMapper.mapDtoToEntity(characterDto.origin()));
        character.setLocation(characterLocationMapper.mapDtoToEntity(characterDto.location()));
        character.setImageUrl(characterDto.url());
        character.setCreated(characterDto.created());
        character.setEpisodes(new HashSet<>() ); //todo
        return character;
    }
}
