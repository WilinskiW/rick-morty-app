package com.rick_morty.rick_morty_web_api.api.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EpisodeMapperTest {

    private EpisodeMapper episodeMapper;

    @BeforeEach
    void setUp() {
        episodeMapper = new EpisodeMapper();
    }

    @Test
    void testEntityToDto() {
        Episode episode = new Episode();
        episode.setId(1);
        episode.setName("Pilot");
        episode.setAirDate(LocalDate.of(2013, 12, 2).toString());
        episode.setEpisode("S01E01");

        Character character1 = new Character();
        character1.setId(1);
        character1.setName("Rick Sanchez");
        character1.setStatus("Alive");
        character1.setSpecies("Human");
        character1.setType("Scientist");
        character1.setGender("Male");
        character1.setImageUrl("https://rickandmortyapi.com/api/character/avatar/1.jpeg");

        Location location = new Location();
        location.setId(1);
        location.setName("Earth");
        location.setType("Planet");
        location.setDimension("Dimension C-137");
        character1.setOrigin(location);
        character1.setLocation(location);

        Set<Character> characters = new HashSet<>();
        characters.add(character1);
        episode.setCharacters(characters);

        EpisodeDto dto = episodeMapper.entityToDto(episode);

        assertEquals(episode.getId(), dto.getId());
        assertEquals(episode.getName(), dto.getTitle());
        assertEquals(episode.getAirDate(), dto.getAirDate());
        assertEquals(episode.getEpisode(), dto.getEpisode());
        assertEquals(1, dto.getCharacters().size());

        assertEquals(character1.getId(), dto.getCharacters().getFirst().getId());
        assertEquals(character1.getName(), dto.getCharacters().getFirst().getName());
        assertEquals(character1.getStatus(), dto.getCharacters().getFirst().getStatus());
        assertEquals(character1.getSpecies(), dto.getCharacters().getFirst().getSpecies());
        assertEquals(character1.getType(), dto.getCharacters().getFirst().getType());
        assertEquals(character1.getGender(), dto.getCharacters().getFirst().getGender());
        assertEquals(character1.getImageUrl(), dto.getCharacters().getFirst().getImageUrl());

        assertNotNull(dto.getCharacters().getFirst().getOrigin());
        assertEquals(location.getId(), dto.getCharacters().getFirst().getOrigin().getId());
        assertEquals(location.getName(), dto.getCharacters().getFirst().getOrigin().getName());
        assertEquals(location.getType(), dto.getCharacters().getFirst().getOrigin().getType());
        assertEquals(location.getDimension(), dto.getCharacters().getFirst().getOrigin().getDimension());
    }

    @Test
    void testDtoToEntity() {
        EpisodeDto dto = new EpisodeDto(
                1,
                "Pilot",
                LocalDate.of(2013, 12, 2).toString(),
                "S01E01",
                null
        );

        Episode episode = episodeMapper.dtoToEntity(dto);

        assertEquals(dto.getId(), episode.getId());
        assertEquals(dto.getTitle(), episode.getName());
        assertEquals(dto.getAirDate(), episode.getAirDate());
        assertEquals(dto.getEpisode(), episode.getEpisode());
        assertNotNull(episode.getCreated());
    }
}