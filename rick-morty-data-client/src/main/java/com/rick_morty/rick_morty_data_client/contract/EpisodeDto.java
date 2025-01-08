package com.rick_morty.rick_morty_data_client.contract;

import java.time.LocalDateTime;
import java.util.List;

public record EpisodeDto(int id,
                         String name,
                         String air_date,
                         String episode,
                         List<CharacterDto> characters,
                         String url,
                         LocalDateTime created) {
}
