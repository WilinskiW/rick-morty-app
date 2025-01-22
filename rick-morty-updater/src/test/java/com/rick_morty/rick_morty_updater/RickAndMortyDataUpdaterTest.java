package com.rick_morty.rick_morty_updater;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.CharacterRepository;
import com.rick_morty.rick_morty_data.repository.EpisodeRepository;
import com.rick_morty.rick_morty_data.repository.LocationRepository;
import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_data_client.RickAndMortyDataPuller;
import com.rick_morty.rick_morty_data_client.contract.*;
import com.rick_morty.rick_morty_updater.mapper.DtoToEntityMapper;
import com.rick_morty.rick_morty_updater.updater.RickAndMortyDataUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

public class RickAndMortyDataUpdaterTest {
    @Mock
    private RickAndMortyDataPuller apiClient;
    @Mock
    private RickAndMortyDbCataloger dbCataloger;
    @Mock
    private DtoToEntityMapper mapper;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private EpisodeRepository episodeRepository;
    @Mock
    private CharacterRepository characterRepository;

    private RickAndMortyDataUpdater updater;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(dbCataloger.getLocations()).thenReturn(locationRepository);
        when(dbCataloger.getEpisodes()).thenReturn(episodeRepository);
        when(dbCataloger.getCharacters()).thenReturn(characterRepository);
        updater = new RickAndMortyDataUpdater(apiClient, dbCataloger, mapper);
    }

    @Test
    void testSyncData() {
        // Arrange
        LocationDto locationDto = new LocationDto(1, "Earth", "Planet",
                "Dimension C-137", List.of(), "", LocalDateTime.now());
        EpisodeDto episodeDto = new EpisodeDto(1, "Pilot", "December 2, 2013",
                "S01E01", List.of(), "", LocalDateTime.now());
        CharacterDto characterDto = new CharacterDto(
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
                "https://rickandmortyapi.com/api/character/1",
                LocalDateTime.now()
        );

        Location location = new Location();
        location.setSourceId(1);
        Episode episode = new Episode();
        episode.setSourceId(1);
        Character character = new Character();
        character.setSourceId(1);

        when(apiClient.getLocations()).thenReturn(List.of(locationDto));
        when(apiClient.getEpisodes()).thenReturn(List.of(episodeDto));
        when(apiClient.getCharactersPage(1)).thenReturn(new ResultCharactersDto(
                new InfoDto(1, 1, null, null),
                List.of(characterDto)
        ));

        when(mapper.mapToEntity(locationDto)).thenReturn(location);
        when(mapper.mapToEntity(episodeDto)).thenReturn(episode);
        when(mapper.mapToEntity(eq(characterDto), any(), any(), any())).thenReturn(character);

        when(locationRepository.existsBySourceId(1)).thenReturn(false);
        when(episodeRepository.existsBySourceId(1)).thenReturn(false);
        when(characterRepository.existsBySourceId(1)).thenReturn(false);

        // Act
        updater.syncData();

        // Assert
        verify(locationRepository).save(location);
        verify(episodeRepository).save(episode);
        verify(characterRepository).save(character);
    }

    @Test
    void testSyncDataWithExistingEntities() {
        // Arrange
        LocationDto locationDto = new LocationDto(1, "Earth", "Planet", "Dimension C-137",
                List.of(), "https://rickandmortyapi.com/api/location/1", LocalDateTime.now());
        EpisodeDto episodeDto = new EpisodeDto(1, "Pilot", "December 2, 2013", "S01E01",
                List.of(), "https://rickandmortyapi.com/api/episode/1", LocalDateTime.now());
        CharacterDto characterDto = new CharacterDto(
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
                "https://rickandmortyapi.com/api/character/1",
                LocalDateTime.now()

        );

        Location location = new Location();
        location.setSourceId(1);
        Episode episode = new Episode();
        episode.setSourceId(1);
        Character character = new Character();
        character.setSourceId(1);

        when(apiClient.getLocations()).thenReturn(List.of(locationDto));
        when(apiClient.getEpisodes()).thenReturn(List.of(episodeDto));
        when(apiClient.getCharactersPage(1)).thenReturn(new ResultCharactersDto(
                new InfoDto(1, 1, null, null),
                List.of(characterDto)
        ));

        when(mapper.mapToEntity(locationDto)).thenReturn(location);
        when(mapper.mapToEntity(episodeDto)).thenReturn(episode);
        when(mapper.mapToEntity(eq(characterDto), any(), any(), any())).thenReturn(character);

        when(locationRepository.existsBySourceId(1)).thenReturn(true);
        when(episodeRepository.existsBySourceId(1)).thenReturn(true);
        when(characterRepository.existsBySourceId(1)).thenReturn(true);

        // Act
        updater.syncData();

        // Assert
        verify(locationRepository, never()).save(any());
        verify(episodeRepository, never()).save(any());
        verify(characterRepository, never()).save(any());
    }
}
