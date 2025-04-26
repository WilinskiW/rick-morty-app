package com.rick_morty.rick_morty_web_api.api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.repository.web.CharacterRepository;
import com.rick_morty.rick_morty_data.repository.web.EpisodeRepository;
import com.rick_morty.rick_morty_data.repository.web.LocationRepository;
import com.rick_morty.rick_morty_data.repository.web.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import com.rick_morty.rick_morty_web_api.api.exception.DataNotFoundException;
import com.rick_morty.rick_morty_web_api.api.mapper.CharacterMapper;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

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
    void testSaveWhenCharacterAlreadyExist() {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setName("Rick");

        when(characterRepository.existsByName(characterDto.getName())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> characterService.save(characterDto));
    }

    @Test
    void testGetAll() {
        int pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, 25);
        List<Character> characters = Arrays.asList(new Character(), new Character());
        Page<Character> characterPage = new PageImpl<>(characters, pageable, characters.size());
        Page<CharacterDto> characterDtoPage = new PageImpl<>(Arrays.asList(new CharacterDto(), new CharacterDto()), pageable, characters.size());

        when(characterRepository.findAll(pageable)).thenReturn(characterPage);
        when(mapper.entityListToDtoPage(characterPage)).thenReturn(characterDtoPage);

        Page<CharacterDto> result = characterService.getAll(pageNumber);

        assertEquals(characterDtoPage, result);
    }

    @Test
    void testGetAllNotInEpisode() {
        int episodeId = 1;
        List<Character> characters = Arrays.asList(new Character(), new Character());
        List<CharacterDto> characterDtos = List.of(new CharacterDto());

        when(characterRepository.findByEpisodeNotIn(episodeId)).thenReturn(characters);
        when(mapper.entityListToDtoList(characters)).thenReturn(characterDtos);

        List<CharacterDto> result = characterService.getAllNotInTheEpisode(episodeId);

        assertEquals(characterDtos, result);
    }

    @Test
    void testGetAllNotInLocation() {
        int locationId = 1;
        List<Character> characters = Arrays.asList(new Character(), new Character());
        List<CharacterDto> characterDtos = List.of(new CharacterDto());

        when(characterRepository.findByLocationNotIn(locationId)).thenReturn(characters);
        when(mapper.entityListToDtoList(characters)).thenReturn(characterDtos);

        List<CharacterDto> result = characterService.getAllNotInTheLocation(locationId);

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
        int id = 1;
        Character character = new Character();
        character.setEpisodes(new HashSet<>());

        when(characterRepository.findById(id)).thenReturn(Optional.of(character));

        characterService.deleteById(id);

        verify(characterRepository).delete(character);
    }

    @Test
    void testDeleteByIdWithEpisodes() {
        int id = 1;
        Character character = new Character();
        Episode episode = new Episode();
        character.setEpisodes(new HashSet<>(Collections.singletonList(episode)));
        episode.setCharacters(new HashSet<>(Collections.singletonList(character)));

        when(characterRepository.findById(id)).thenReturn(Optional.of(character));

        characterService.deleteById(id);

        verify(episodeRepository).save(episode);
        verify(characterRepository).delete(character);
    }
}
