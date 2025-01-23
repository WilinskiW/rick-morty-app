package com.rick_morty.rick_morty_ui.converters;

import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import com.rick_morty.rick_morty_web_api.api.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationSummaryConverter implements Converter<String, LocationSummaryDto> {
    private final LocationService locationService;

    @Override
    public LocationSummaryDto convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocationDto.toSummary(locationService.getById(Integer.parseInt(source)));
    }
}
