package com.rick_morty.rick_morty_web_api.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.contract.LocationSummaryDto;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper implements EntityToDtoMapper<Character, CharacterSummaryDto> {
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
                locationToDto(character.getLocation())
        );
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
