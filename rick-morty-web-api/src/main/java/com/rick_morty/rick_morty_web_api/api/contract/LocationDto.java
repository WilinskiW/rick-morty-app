package com.rick_morty.rick_morty_web_api.api.contract;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private int id;
    @NotEmpty(message = "Name can't be empty")
    private String name;
    @Size(min = 3, max = 50, message = "Type is too short or too long")
    private String type;
    @NotEmpty(message = "Dimension can't be empty")
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