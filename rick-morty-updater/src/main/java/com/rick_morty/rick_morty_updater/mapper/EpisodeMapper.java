package com.rick_morty.rick_morty_updater.mapper;

import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data_client.contract.EpisodeDto;
import org.springframework.stereotype.Component;

@Component
public class EpisodeMapper implements DtoEntityMapper<EpisodeDto, Episode> {
    @Override
    public Episode mapDtoToEntity(EpisodeDto episodeDto) {
        Episode episode = new Episode();
        episode.setId(episodeDto.id());
        episode.setName(episodeDto.name());
        episode.setAirDate(episode.getAirDate());
        episode.setEpisode(episode.getEpisode());
        episode.setCreated(episode.getCreated());
        episode.setCharacters(episode.getCharacters());
        return episode;
    }
}