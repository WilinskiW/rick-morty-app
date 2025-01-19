package com.rick_morty.rick_morty_ui.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LocationDto {
    private int id;
    private String name;
    private String type;
    private String dimension;
    private List<CharacterDto> residents = new ArrayList<>();

    public CharacterDto.LocationSummaryDto toLocationSummaryDto() {
        CharacterDto.LocationSummaryDto dto = new CharacterDto.LocationSummaryDto();
        dto.setId(id);
        dto.setName(name);
        dto.setType(type);
        dto.setDimension(dimension);
        return dto;
    }
}
