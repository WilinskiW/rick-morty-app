package com.rick_morty.rick_morty_web_api.api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.*;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import com.rick_morty.rick_morty_web_api.api.exception.DataNotFoundException;
import com.rick_morty.rick_morty_web_api.api.mapper.CharacterMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    @Mock
    private RickAndMortyDbCataloger dbCataloger;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private CharacterMapper mapper;

    @InjectMocks
    private CharacterService characterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(dbCataloger.getCharacters()).thenReturn(characterRepository);
        when(dbCataloger.getLocations()).thenReturn(locationRepository);
        when(dbCataloger.getEpisodes()).thenReturn(episodeRepository);
    }

    @Test
    void testSave() {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setOrigin(new LocationSummaryDto());
        characterDto.setCurrentLocation(new LocationSummaryDto());
        Character character = new Character();
        when(mapper.dtoToEntity(characterDto)).thenReturn(character);
        when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));

        characterService.save(characterDto);

        verify(characterRepository).save(character);
    }

    @Test
    void testGetAll() {
        List<Character> characters = Arrays.asList(new Character(), new Character());
        List<CharacterDto> characterDtos = Arrays.asList(new CharacterDto(), new CharacterDto());
        when(characterRepository.findAll()).thenReturn(characters);
        when(mapper.entityListToDtoList(characters)).thenReturn(characterDtos);

        List<CharacterDto> result = characterService.getAll();

        assertEquals(characterDtos, result);
    }

    @Test
    void testGetCharacterById() {
        Integer id = 1;
        Character character = new Character();
        CharacterDto characterDto = new CharacterDto();
        when(characterRepository.findById(id)).thenReturn(Optional.of(character));
        when(mapper.entityToDto(character)).thenReturn(characterDto);

        CharacterDto result = characterService.getCharacterById(id);

        assertEquals(characterDto, result);
    }

    @Test
    void testGetCharacterByIdNotFound() {
        Integer id = 1;
        when(characterRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> characterService.getCharacterById(id));
    }

    @Test
    void testUpdate() {
        Integer id = 1;
        CharacterDto characterDto = new CharacterDto();
        characterDto.setOrigin(new LocationSummaryDto());
        characterDto.setCurrentLocation(new LocationSummaryDto());
        Character character = new Character();
        when(characterRepository.findById(id)).thenReturn(Optional.of(character));
        when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));

        characterService.update(id, characterDto);

        verify(characterRepository).save(character);
    }

    @Test
    void testDeleteById() {
        Integer id = 1;
        Character character = new Character();
        character.setEpisodes(new HashSet<>());
        when(characterRepository.findById(id)).thenReturn(Optional.of(character));

        characterService.deleteById(id);

        verify(characterRepository).delete(character);
    }

    @Test
    void testGetAllLikeName() {
        String like = "Rick";
        List<Character> characters = Arrays.asList(new Character(), new Character());
        List<CharacterDto> characterDtos = Arrays.asList(new CharacterDto(), new CharacterDto());
        when(characterRepository.findLike("%" + like + "%")).thenReturn(characters);
        when(mapper.entityListToDtoList(characters)).thenReturn(characterDtos);

        List<CharacterDto> result = characterService.getAllLikeName(like);

        assertEquals(characterDtos, result);
    }

    @Test
    void testGetScheduleCharacter() {
        CharacterDto characterDto = new CharacterDto();
        when(characterRepository.count()).thenReturn(10L);
        when(characterRepository.findById(anyInt())).thenReturn(Optional.of(new Character()));
        when(mapper.entityToDto(any(Character.class))).thenReturn(characterDto);

        CharacterDto result = characterService.getScheduleCharacter();

        assertNotNull(result);
        assertEquals(characterDto, result);
    }

    @Test
    void testSetScheduleCharacter() {
        CharacterDto characterDto = new CharacterDto();
        when(characterRepository.count()).thenReturn(10L);
        when(characterRepository.findById(anyInt())).thenReturn(Optional.of(new Character()));
        when(mapper.entityToDto(any(Character.class))).thenReturn(characterDto);

        characterService.setScheduleCharacter();

        assertNotNull(characterService.getScheduleCharacter());
    }
}