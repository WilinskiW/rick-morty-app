package com.rick_morty.rick_morty_web_api.api.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDto {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private LocationSummaryDto origin;
    private LocationSummaryDto currentLocation;
    private String imageUrl;
}