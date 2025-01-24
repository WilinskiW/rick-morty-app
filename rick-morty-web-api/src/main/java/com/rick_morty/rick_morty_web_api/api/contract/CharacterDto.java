package com.rick_morty.rick_morty_web_api.api.contract;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDto {
    private int id;
    @NotEmpty(message = "Name can't be empty")
    private String name;
    @NotNull(message = "Status can't be null")
    private String status;
    @NotNull(message = "Species can't be null")
    private String species;
    private String type;
    @NotNull(message = "Gender can't be null")
    private String gender;
    private LocationSummaryDto origin;
    private LocationSummaryDto currentLocation;
    private String imageUrl;
}