package com.rick_morty.rick_morty_data_client.contract;

import java.time.LocalDateTime;
import java.util.List;

public record CharacterDto(int id,
                           String name,
                           String status,
                           String species,
                           String type,
                           String gender,
                           LocationDto origin,
                           LocationDto location,
                           String image,
                           List<String> episode,
                           String url,
                           LocalDateTime created) {

    public record LocationDto(String name, String url) {}
}

