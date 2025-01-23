package com.rick_morty.rick_morty_web_api.api.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
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

    private List<CharacterDto> charactersToDto(Set<Character> characters) {
        return characters.stream()
                .map(character ->
                        new CharacterDto(character.getId(),character.getName(),
                        character.getStatus(), character.getSpecies(), character.getType(), character.getGender(),
                        locationSummaryDto(character.getOrigin()),
                        locationSummaryDto(character.getLocation()),
                        character.getImageUrl()))
                .toList();
    }

    private LocationSummaryDto locationSummaryDto(Location location) {
        if(location == null) {
            return null;
        }
        return new LocationSummaryDto(location.getId(), location.getName(), location.getType(), location.getDimension());
    }

    @Override
    public Episode dtoToEntity(EpisodeDto dto) {
        Episode episode = new Episode();
        episode.setId(dto.getId());
        episode.setName(dto.getTitle());
        episode.setAirDate(dto.getAirDate());
        episode.setEpisode(dto.getEpisode());
        episode.setCreated(LocalDateTime.now());
        return episode;
    }
}
