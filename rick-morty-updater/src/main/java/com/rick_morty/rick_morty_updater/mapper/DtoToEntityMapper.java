package com.rick_morty.rick_morty_updater.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data_client.contract.CharacterDto;
import com.rick_morty.rick_morty_data_client.contract.EpisodeDto;
import com.rick_morty.rick_morty_data_client.contract.LocationDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DtoToEntityMapper {
    public Character mapToEntity(CharacterDto dto, Location origin, Location location, Set<Episode> episodes) {
        Character character = new Character();
        character.setSourceId(dto.id());
        character.setName(dto.name());
        character.setStatus(dto.status());
        character.setSpecies(dto.species());
        character.setType(dto.type());
        character.setGender(dto.gender());
        character.setImageUrl(dto.image());
        character.setCreated(dto.created());
        character.setOrigin(origin);
        character.setLocation(location);
        character.setEpisodes(episodes);
        return character;
    }

    public Episode mapToEntity(EpisodeDto dto) {
        Episode episode = new Episode();
        episode.setSourceId(dto.id());
        episode.setName(dto.name());
        episode.setAirDate(dto.air_date());
        episode.setEpisode(dto.episode());
        episode.setCreated(dto.created());
        return episode;
    }

    public Location mapToEntity(LocationDto dto) {
        Location location = new Location();
        location.setSourceId(dto.id());
        location.setName(dto.name());
        location.setType(dto.type());
        location.setDimension(dto.dimension());
        location.setCreated(dto.created());
        return location;
    }
}
