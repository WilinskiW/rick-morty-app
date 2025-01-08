package com.rick_morty.rick_morty_data_client.contract;

import java.util.List;

public record EpisodesDto(InfoDto info, List<EpisodeDto> results) {
}
