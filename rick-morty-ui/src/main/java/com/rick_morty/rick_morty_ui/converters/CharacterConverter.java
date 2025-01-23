package com.rick_morty.rick_morty_ui.converters;

import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterConverter implements Converter<String, CharacterDto> {
    private final CharacterService characterService;

    @Override
    public CharacterDto convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return characterService.getCharacterById(Integer.parseInt(source));
    }
}
