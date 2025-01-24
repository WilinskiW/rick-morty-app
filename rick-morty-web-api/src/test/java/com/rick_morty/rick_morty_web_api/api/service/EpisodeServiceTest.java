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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EpisodeServiceTest {

    @Mock
    private RickAndMortyDbCataloger dbCataloger;

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private EpisodeMapper episodeMapper;

    @InjectMocks
    private EpisodeService episodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(dbCataloger.getEpisodes()).thenReturn(episodeRepository);
        when(dbCataloger.getCharacters()).thenReturn(characterRepository);
    }

    @Test
    void testSave() {
        EpisodeDto episodeDto = new EpisodeDto();
        Episode episode = new Episode();
        when(episodeMapper.dtoToEntity(episodeDto)).thenReturn(episode);

        episodeService.save(episodeDto);

        verify(episodeRepository).save(episode);
    }

    @Test
    void testGetAll() {
        List<Episode> episodes = Arrays.asList(new Episode(), new Episode());
        when(episodeRepository.findAll()).thenReturn(episodes);
        when(episodeMapper.entityToDto(any(Episode.class))).thenReturn(new EpisodeDto());

        List<EpisodeDto> result = episodeService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testGetById() {
        Integer id = 1;
        Episode episode = new Episode();
        EpisodeDto episodeDto = new EpisodeDto();
        when(episodeRepository.findById(id)).thenReturn(Optional.of(episode));
        when(episodeMapper.entityToDto(episode)).thenReturn(episodeDto);

        EpisodeDto result = episodeService.getById(id);

        assertEquals(episodeDto, result);
    }

    @Test
    void testGetByIdNotFound() {
        Integer id = 1;
        when(episodeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> episodeService.getById(id));
    }

    @Test
    void testUpdate() {
        Integer id = 1;
        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setCharacters(Arrays.asList(new CharacterDto()));
        Episode episode = new Episode();
        when(episodeRepository.findById(id)).thenReturn(Optional.of(episode));
        when(characterRepository.findById(any())).thenReturn(Optional.of(new Character()));

        episodeService.update(id, episodeDto);

        verify(episodeRepository).save(episode);
    }

    @Test
    void testDeleteById() {
        Integer id = 1;
        Episode episode = new Episode();
        when(episodeRepository.findById(id)).thenReturn(Optional.of(episode));

        episodeService.deleteById(id);

        verify(episodeRepository).delete(episode);
    }

    @Test
    void testRemoveCharacterFromLocation() {
        Integer episodeId = 1;
        Integer characterId = 2;
        Episode episode = new Episode();
        Character character = new Character();
        episode.setCharacters(new HashSet<>(Collections.singletonList(character)));
        character.setEpisodes(new HashSet<>(Collections.singletonList(episode)));

        when(episodeRepository.findById(episodeId)).thenReturn(Optional.of(episode));
        when(characterRepository.findById(characterId)).thenReturn(Optional.of(character));

        episodeService.removeCharacterFromLocation(episodeId, characterId);

        verify(episodeRepository).save(episode);
        verify(characterRepository).save(character);
        assertTrue(episode.getCharacters().isEmpty());
        assertTrue(character.getEpisodes().isEmpty());
    }
}