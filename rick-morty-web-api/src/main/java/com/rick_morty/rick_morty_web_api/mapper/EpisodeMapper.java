package com.rick_morty.rick_morty_web_api.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_web_api.contract.EpisodeDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class EpisodeMapper implements Mapper<Episode, EpisodeDto> {
    @Override
    public EpisodeDto entityToDto(Episode episode) {
        return new EpisodeDto(episode.getId(),
                episode.getName(),
                episode.getAirDate(),
                episode.getEpisode(),
                charactersToDto(episode.getCharacters())
        );
    }

    private List<EpisodeDto.Characters> charactersToDto(Set<Character> characters) {
        return characters.stream()
                .map(character -> new EpisodeDto.Characters(character.getId(),character.getName()))
                .toList();
    }

    @Override
    public Episode dtoToEntity(EpisodeDto dto) {
        Episode episode = new Episode();
        episode.setId(dto.id());
        episode.setName(dto.title());
        episode.setAirDate(dto.airDate());
        episode.setEpisode(dto.episode());
        episode.setCreated(LocalDateTime.now());
        return episode;
    }
}
