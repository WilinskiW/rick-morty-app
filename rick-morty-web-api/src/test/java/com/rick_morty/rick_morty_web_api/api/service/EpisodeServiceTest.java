package com.rick_morty.rick_morty_web_api.api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.repository.web.CharacterRepository;
import com.rick_morty.rick_morty_data.repository.web.EpisodeRepository;
import com.rick_morty.rick_morty_data.repository.web.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.exception.DataNotFoundException;
import com.rick_morty.rick_morty_web_api.api.mapper.EpisodeMapper;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EpisodeServiceTest {

    @Mock
    private RickAndMortyDbCataloger dbCataloger;

    @Mock
    private EpisodeMapper episodeMapper;

    @InjectMocks
    private EpisodeService episodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setTitle("Test Episode");

        var episodeRepository = mockDbEpisodes();
        when(episodeRepository.existsByName(episodeDto.getTitle())).thenReturn(false);
        when(episodeMapper.dtoToEntity(episodeDto)).thenReturn(new Episode());

        episodeService.save(episodeDto);

        verify(episodeRepository).save(any(Episode.class));
    }

    @Test
    void testSaveWhenTitleAlreadyExists() {
        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setTitle("Duplicate Title");

        var episodeRepository = mockDbEpisodes();
        when(episodeRepository.existsByName(episodeDto.getTitle())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> episodeService.save(episodeDto));
    }

    @Test
    void testGetAll() {
        var episodeRepository = mockDbEpisodes();
        Page<Episode> page = new PageImpl<>(List.of(new Episode(), new Episode()));
        when(episodeRepository.findAll(any(PageRequest.class))).thenReturn(page);

        when(episodeMapper.entityListToDtoPage(any(Page.class))).thenReturn(new PageImpl<>(List.of(new EpisodeDto(), new EpisodeDto())));

        Page<EpisodeDto> result = episodeService.getAll(0);

        assertEquals(2, result.getContent().size());
    }

    @Test
    void testGetByIdSuccess() {
        int id = 1;
        var episodeRepository = mockDbEpisodes();
        Episode episode = new Episode();
        EpisodeDto episodeDto = new EpisodeDto();

        when(episodeRepository.findById(id)).thenReturn(Optional.of(episode));
        when(episodeMapper.entityToDto(episode)).thenReturn(episodeDto);

        EpisodeDto result = episodeService.getById(id);

        assertEquals(episodeDto, result);
    }

    @Test
    void testGetByIdNotFound() {
        int id = 1;
        var episodeRepository = mockDbEpisodes();
        when(episodeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> episodeService.getById(id));
    }

    @Test
    void testUpdateSuccess() {
        int id = 1;
        EpisodeDto episodeDto = new EpisodeDto();
        CharacterDto characterDto = new CharacterDto();
        characterDto.setId(2);
        episodeDto.setCharacters(List.of(characterDto));
        episodeDto.setTitle("Updated Title");
        episodeDto.setEpisode("S01E01");
        episodeDto.setAirDate("December 2, 2013");

        var episodeRepository = mockDbEpisodes();
        var characterRepository = mockDbCharacters();

        Episode episode = new Episode();
        when(episodeRepository.findById(id)).thenReturn(Optional.of(episode));
        when(characterRepository.findById(characterDto.getId())).thenReturn(Optional.of(new Character()));

        episodeService.update(id, episodeDto);

        verify(episodeRepository).save(episode);
    }

    @Test
    void testUpdateNotFound() {
        int id = 1;
        EpisodeDto episodeDto = new EpisodeDto();

        var episodeRepository = mockDbEpisodes();
        when(episodeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> episodeService.update(id, episodeDto));
    }

    @Test
    void testDeleteByIdSuccess() {
        int id = 1;
        var episodeRepository = mockDbEpisodes();
        Episode episode = new Episode();
        when(episodeRepository.findById(id)).thenReturn(Optional.of(episode));

        episodeService.deleteById(id);

        verify(episodeRepository).delete(episode);
    }

    @Test
    void testDeleteByIdNotFound() {
        int id = 1;
        var episodeRepository = mockDbEpisodes();
        when(episodeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> episodeService.deleteById(id));
    }

    @Test
    void testRemoveCharacterFromLocationSuccess() {
        int episodeId = 1;
        int characterId = 2;

        var episodeRepository = mockDbEpisodes();
        var characterRepository = mockDbCharacters();

        Episode episode = new Episode();
        Character character = new Character();
        episode.setCharacters(new HashSet<>(Set.of(character)));
        character.setEpisodes(new HashSet<>(Set.of(episode)));

        when(episodeRepository.findById(episodeId)).thenReturn(Optional.of(episode));
        when(characterRepository.findById(characterId)).thenReturn(Optional.of(character));

        episodeService.removeCharacterFromLocation(episodeId, characterId);

        verify(episodeRepository).save(episode);
        verify(characterRepository).save(character);
        assertTrue(episode.getCharacters().isEmpty());
        assertTrue(character.getEpisodes().isEmpty());
    }

    @Test
    void testRemoveCharacterFromLocationNotFoundEpisode() {
        int episodeId = 1;
        int characterId = 2;

        var episodeRepository = mockDbEpisodes();
        when(episodeRepository.findById(episodeId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> episodeService.removeCharacterFromLocation(episodeId, characterId));
    }

    @Test
    void testRemoveCharacterFromLocationNotFoundCharacter() {
        int episodeId = 1;
        int characterId = 2;

        var episodeRepository = mockDbEpisodes();
        var characterRepository = mockDbCharacters();

        when(episodeRepository.findById(episodeId)).thenReturn(Optional.of(new Episode()));
        when(characterRepository.findById(characterId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> episodeService.removeCharacterFromLocation(episodeId, characterId));
    }


    private EpisodeRepository mockDbEpisodes() {
        var repo = mock(com.rick_morty.rick_morty_data.repository.web.EpisodeRepository.class);
        when(dbCataloger.getEpisodes()).thenReturn(repo);
        return repo;
    }

    private CharacterRepository mockDbCharacters() {
        var repo = mock(com.rick_morty.rick_morty_data.repository.web.CharacterRepository.class);
        when(dbCataloger.getCharacters()).thenReturn(repo);
        return repo;
    }
}
