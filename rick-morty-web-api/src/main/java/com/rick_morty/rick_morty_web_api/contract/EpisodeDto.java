package com.rick_morty.rick_morty_web_api.contract;

import java.util.List;

public record EpisodeDto(int id,
                         String title,
                         String airDate,
                         String episode,
                         List<CharacterSummaryDto> characters) {
}
