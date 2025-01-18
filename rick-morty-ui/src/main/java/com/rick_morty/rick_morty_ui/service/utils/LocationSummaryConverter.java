package com.rick_morty.rick_morty_ui.service.utils;

import com.rick_morty.rick_morty_ui.dto.CharacterDto;
import com.rick_morty.rick_morty_ui.service.AppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LocationSummaryConverter implements Converter<String, CharacterDto.LocationSummaryDto> {
    private final AppClient appClient;

    @Override
    public CharacterDto.LocationSummaryDto convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return appClient.getLocationSummary(Integer.parseInt(source));
    }
}
