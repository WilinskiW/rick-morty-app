package com.rick_morty.rick_morty_updater;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data_client.contract.CharacterDto;
import com.rick_morty.rick_morty_data_client.contract.EpisodeDto;
import com.rick_morty.rick_morty_data_client.contract.LocationDto;
import com.rick_morty.rick_morty_updater.mapper.DtoToEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DtoToEntityMapperTest {
    private DtoToEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DtoToEntityMapper();
    }

    @Test
    void testMapToEntityCharacter() {
        // Arrange
        CharacterDto dto = new CharacterDto(
                1,
                "Rick Sanchez",
                "Alive",
                "Human",
                "",
                "Male",
                new CharacterDto.LocationDto("Earth", "https://rickandmortyapi.com/api/location/1"),
                new CharacterDto.LocationDto("Earth", "https://rickandmortyapi.com/api/location/20"),
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                List.of("https://rickandmortyapi.com/api/episode/1"),
                "https://rickandmortyapi.com/api/episode/1",
                LocalDateTime.now()
        );
        Location origin = new Location();
        origin.setName("Earth");
        Location location = new Location();
        location.setName("Earth");
        Set<Episode> episodes = new HashSet<>();
        Episode episode = new Episode();
        episode.setSourceId(1);
        episodes.add(episode);

        Character result = mapper.mapToEntity(dto, origin, location, episodes);

        assertNotNull(result);
        assertEquals(dto.id(), result.getSourceId());
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.status(), result.getStatus());
        assertEquals(dto.species(), result.getSpecies());
        assertEquals(dto.type(), result.getType());
        assertEquals(dto.gender(), result.getGender());
        assertEquals(dto.image(), result.getImageUrl());
        assertEquals(dto.created(), result.getCreated());
        assertEquals(origin, result.getOrigin());
        assertEquals(location, result.getLocation());
        assertEquals(episodes, result.getEpisodes());
    }

    @Test
    void testMapToEntityEpisode() {
        // Arrange
        EpisodeDto dto = new EpisodeDto(
                1,
                "Pilot",
                "December 2, 2013",
                "S01E01",
                List.of("https://rickandmortyapi.com/api/character/1"),
                "https://rickandmortyapi.com/api/episode/1",
                LocalDateTime.now()
        );

        // Act
        Episode result = mapper.mapToEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.id(), result.getSourceId());
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.air_date(), result.getAirDate());
        assertEquals(dto.episode(), result.getEpisode());
        assertEquals(dto.created(), result.getCreated());
        assertNotNull(result.getCharacters());
        assertTrue(result.getCharacters().isEmpty());
    }

    @Test
    void testMapToEntityLocation() {
        // Arrange
        LocationDto dto = new LocationDto(
                1,
                "Earth",
                "Planet",
                "Dimension C-137",
                List.of("https://rickandmortyapi.com/api/character/1"),
                "https://rickandmortyapi.com/api/location/1",
                LocalDateTime.now()
        );

        Location result = mapper.mapToEntity(dto);

        assertNotNull(result);
        assertEquals(dto.id(), result.getSourceId());
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.type(), result.getType());
        assertEquals(dto.dimension(), result.getDimension());
        assertEquals(dto.created(), result.getCreated());
        assertNotNull(result.getCurrentCharacters());
        assertTrue(result.getCurrentCharacters().isEmpty());
        assertNotNull(result.getOriginCharacters());
        assertTrue(result.getOriginCharacters().isEmpty());
    }
}
