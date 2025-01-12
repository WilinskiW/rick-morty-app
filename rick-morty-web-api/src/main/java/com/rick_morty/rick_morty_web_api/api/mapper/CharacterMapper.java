package com.rick_morty.rick_morty_web_api.api.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CharacterMapper implements Mapper<Character, CharacterSummaryDto> {
    @Override
    public CharacterSummaryDto entityToDto(Character character) {
        return new CharacterSummaryDto(
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
    public Character dtoToEntity(CharacterSummaryDto characterSummaryDto) {
        Character character = new Character();
        character.setSourceId(characterSummaryDto.id());
        character.setName(characterSummaryDto.name());
        character.setStatus(characterSummaryDto.status());
        character.setSpecies(characterSummaryDto.species());
        character.setType(characterSummaryDto.type());
        character.setGender(characterSummaryDto.gender());
        character.setOrigin(character.getOrigin());
        character.setOrigin(character.getLocation());
        character.setImageUrl(characterSummaryDto.imageUrl());
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
