package com.rick_morty.rick_morty_ui.dto;

import java.util.List;

public record EpisodeDto(int id,
                         String title,
                         String airDate,
                         String episode,
                         List<EpisodeCharacter> characters
) {
    public record EpisodeCharacter(int id, String name){}
}
