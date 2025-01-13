package com.rick_morty.rick_morty_ui.dto;


public record CharacterDto(int id,
                           String name,
                           String status,
                           String species,
                           String type,
                           String gender,
                           LocationSummaryDto origin,
                           LocationSummaryDto currentLocation,
                           String imageUrl) {
   public record LocationSummaryDto(
            int id,
            String name,
            String type,
            String dimension
    ){}
}
