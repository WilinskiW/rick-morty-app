package com.rick_morty.rick_morty_web_api.api.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LocationMapperTest {

    private LocationMapper locationMapper;

    @BeforeEach
    void setUp() {
        locationMapper = new LocationMapper();
    }

    @Test
    void testEntityToDto() {
        Location location = new Location();
        location.setId(1);
        location.setName("Earth");
        location.setType("Planet");
        location.setDimension("Dimension C-137");

        Character character1 = new Character();
        character1.setId(1);
        character1.setName("Rick Sanchez");
        character1.setStatus("Alive");
        character1.setSpecies("Human");
        character1.setType("Scientist");
        character1.setGender("Male");
        character1.setImageUrl("https://rickandmortyapi.com/api/character/avatar/1.jpeg");
        character1.setOrigin(location);
        character1.setLocation(location);

        Set<Character> characters = new HashSet<>();
        characters.add(character1);
        location.setCurrentCharacters(characters);

        LocationDto dto = locationMapper.entityToDto(location);

        assertEquals(location.getId(), dto.getId());
        assertEquals(location.getName(), dto.getName());
        assertEquals(location.getType(), dto.getType());
        assertEquals(location.getDimension(), dto.getDimension());
        assertEquals(1, dto.getResidents().size());

        assertEquals(character1.getId(), dto.getResidents().getFirst().getId());
        assertEquals(character1.getName(), dto.getResidents().getFirst().getName());
        assertEquals(character1.getStatus(), dto.getResidents().getFirst().getStatus());
        assertEquals(character1.getSpecies(), dto.getResidents().getFirst().getSpecies());
        assertEquals(character1.getType(), dto.getResidents().getFirst().getType());
        assertEquals(character1.getGender(), dto.getResidents().getFirst().getGender());
        assertEquals(character1.getImageUrl(), dto.getResidents().getFirst().getImageUrl());

        assertNotNull(dto.getResidents().getFirst().getOrigin());
        assertEquals(location.getId(), dto.getResidents().getFirst().getOrigin().getId());
        assertEquals(location.getName(), dto.getResidents().getFirst().getOrigin().getName());
        assertEquals(location.getType(), dto.getResidents().getFirst().getOrigin().getType());
        assertEquals(location.getDimension(), dto.getResidents().getFirst().getOrigin().getDimension());
    }

    @Test
    void testDtoToEntity() {
        LocationDto dto = new LocationDto(
                1,
                "Earth",
                "Planet",
                "Dimension C-137",
                null
        );

        Location location = locationMapper.dtoToEntity(dto);

        assertEquals(dto.getId(), location.getSourceId());
        assertEquals(dto.getName(), location.getName());
        assertEquals(dto.getType(), location.getType());
        assertEquals(dto.getDimension(), location.getDimension());
        assertNotNull(location.getCreated());
    }
}