package com.rick_morty.rick_morty_web_api.api.contract;

import java.util.List;

public record LocationDto(
        int id,
        String name,
        String type,
        String dimension,
        List<Residents> residents) {
    public record Residents(int id, String name) {

    }
}
