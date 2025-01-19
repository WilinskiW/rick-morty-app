package com.rick_morty.rick_morty_ui.service.utils;

import com.rick_morty.rick_morty_ui.dto.CharacterDto;
import com.rick_morty.rick_morty_ui.service.AppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterConverter implements Converter<String, CharacterDto> {
    private final AppClient appClient;

    @Override
    public CharacterDto convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return appClient.getCharacter(Integer.parseInt(source));
    }
}
