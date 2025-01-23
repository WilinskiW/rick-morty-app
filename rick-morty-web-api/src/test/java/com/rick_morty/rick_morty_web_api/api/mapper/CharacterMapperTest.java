package com.rick_morty.rick_morty_web_api.api.mapper;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharacterMapperTest {

    private CharacterMapper characterMapper;

    @BeforeEach
    void setUp() {
        characterMapper = new CharacterMapper();
    }

    @Test
    void testEntityToDto() {
        Character character = new Character();
        character.setId(1);
        character.setName("Rick Sanchez");
        character.setStatus("Alive");
        character.setSpecies("Human");
        character.setType("Scientist");
        character.setGender("Male");
        character.setImageUrl("https://rickandmortyapi.com/api/character/avatar/1.jpeg");

        Location origin = new Location();
        origin.setId(1);
        origin.setName("Earth");
        origin.setType("Planet");
        origin.setDimension("Dimension C-137");
        character.setOrigin(origin);

        Location location = new Location();
        location.setId(2);
        location.setName("Citadel of Ricks");
        location.setType("Space station");
        location.setDimension("unknown");
        character.setLocation(location);

        CharacterDto dto = characterMapper.entityToDto(character);

        assertEquals(character.getId(), dto.getId());
        assertEquals(character.getName(), dto.getName());
        assertEquals(character.getStatus(), dto.getStatus());
        assertEquals(character.getSpecies(), dto.getSpecies());
        assertEquals(character.getType(), dto.getType());
        assertEquals(character.getGender(), dto.getGender());
        assertEquals(character.getImageUrl(), dto.getImageUrl());

        assertNotNull(dto.getOrigin());
        assertEquals(origin.getId(), dto.getOrigin().getId());
        assertEquals(origin.getName(), dto.getOrigin().getName());
        assertEquals(origin.getType(), dto.getOrigin().getType());
        assertEquals(origin.getDimension(), dto.getOrigin().getDimension());

        assertNotNull(dto.getCurrentLocation());
        assertEquals(location.getId(), dto.getCurrentLocation().getId());
        assertEquals(location.getName(), dto.getCurrentLocation().getName());
        assertEquals(location.getType(), dto.getCurrentLocation().getType());
        assertEquals(location.getDimension(), dto.getCurrentLocation().getDimension());
    }

    @Test
    void testDtoToEntity() {
        CharacterDto dto = new CharacterDto(
                1,
                "Rick Sanchez",
                "Alive",
                "Human",
                "Scientist",
                "Male",
                new LocationSummaryDto(1, "Earth", "Planet", "Dimension C-137"),
                new LocationSummaryDto(2, "Citadel of Ricks", "Space station", "unknown"),
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        );

        Character character = characterMapper.dtoToEntity(dto);

        assertEquals(dto.getId(), character.getSourceId());
        assertEquals(dto.getName(), character.getName());
        assertEquals(dto.getStatus(), character.getStatus());
        assertEquals(dto.getSpecies(), character.getSpecies());
        assertEquals(dto.getType(), character.getType());
        assertEquals(dto.getGender(), character.getGender());
        assertEquals(dto.getImageUrl(), character.getImageUrl());
        assertNotNull(character.getCreated());
    }

    @Test
    void testEntityListToDtoList() {
        Character character1 = new Character();
        character1.setId(1);
        character1.setName("Rick Sanchez");

        Character character2 = new Character();
        character2.setId(2);
        character2.setName("Morty Smith");

        List<Character> characters = Arrays.asList(character1, character2);

        List<CharacterDto> dtos = characterMapper.entityListToDtoList(characters);

        assertEquals(2, dtos.size());
        assertEquals(character1.getId(), dtos.get(0).getId());
        assertEquals(character1.getName(), dtos.get(0).getName());
        assertEquals(character2.getId(), dtos.get(1).getId());
        assertEquals(character2.getName(), dtos.get(1).getName());
    }
}