package com.rick_morty.rick_morty_data_client.contract;

import java.time.LocalDateTime;
import java.util.List;

public record CharacterDto(int id,
                           String name,
                           String status,
                           String species,
                           String gender,
                           OriginDto origin,
                           LocationDto location,
                           EpisodeDto episode,
                           String url,
                           LocalDateTime created) {


    record OriginDto(String name, String url) {}

    record LocationDto(String image, EpisodeDto episode) {}

    record EpisodeDto(List<String> episode) {}
}

