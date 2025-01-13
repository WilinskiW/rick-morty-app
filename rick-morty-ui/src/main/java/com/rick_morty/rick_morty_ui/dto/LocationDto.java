package com.rick_morty.rick_morty_ui.dto;

import java.util.List;

public record LocationDto(
        int id,
        String name,
        String type,
        String dimension,
        List<CharacterDto> residents
) {
}
