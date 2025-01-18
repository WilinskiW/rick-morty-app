package com.rick_morty.rick_morty_ui.dto;

import lombok.Data;

@Data
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

    @Data
    public static class LocationSummaryDto {
        private int id;
        private String name;
        private String type;
        private String dimension;
    }
}
