package com.rick_morty.rick_morty_web_api.api.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private int id;
    private String name;
    private String type;
    private String dimension;
    private List<CharacterDto> residents;

    public static LocationSummaryDto toSummary(LocationDto locationDto) {
        return new LocationSummaryDto(
                locationDto.getId(),
                locationDto.getName(),
                locationDto.getType(),
                locationDto.getDimension());
    }
}