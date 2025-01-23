package com.rick_morty.rick_morty_web_api.api.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CharacterMapper implements Mapper<Character, CharacterDto> {
    @Override
    public CharacterDto entityToDto(Character character) {
        return new CharacterDto(
                character.getId(),
                character.getName(),
                character.getStatus(),
                character.getSpecies(),
                character.getType(),
                character.getGender(),
                locationToDto(character.getOrigin()),
                locationToDto(character.getLocation()),
                character.getImageUrl());
    }

    @Override
    public Character dtoToEntity(CharacterDto characterDto) {
        Character character = new Character();
        character.setSourceId(characterDto.getId());
        character.setName(characterDto.getName());
        character.setStatus(characterDto.getStatus());
        character.setSpecies(characterDto.getSpecies());
        character.setType(characterDto.getType());
        character.setGender(characterDto.getGender());
        character.setOrigin(character.getOrigin());
        character.setOrigin(character.getLocation());
        character.setImageUrl(characterDto.getImageUrl());
        character.setCreated(LocalDateTime.now());
        return character;
    }


    private LocationSummaryDto locationToDto(Location location) {
        if(location== null){
            return null;
        }

        return new LocationSummaryDto(
                location.getId(),
                location.getName(),
                location.getType(),
                location.getDimension()
        );
    }
}
