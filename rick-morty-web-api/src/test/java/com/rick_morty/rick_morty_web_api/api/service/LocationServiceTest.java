package com.rick_morty.rick_morty_web_api.api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.web.CharacterRepository;
import com.rick_morty.rick_morty_data.repository.web.LocationRepository;
import com.rick_morty.rick_morty_data.repository.web.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.exception.DataNotFoundException;
import com.rick_morty.rick_morty_web_api.api.mapper.LocationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @Mock
    private RickAndMortyDbCataloger dbCataloger;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private LocationMapper mapper;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(dbCataloger.getLocations()).thenReturn(locationRepository);
        when(dbCataloger.getCharacters()).thenReturn(characterRepository);
    }

    @Test
    void testSave() {
        LocationDto locationDto = new LocationDto();
        locationDto.setResidents(Arrays.asList(new CharacterDto()));
        when(characterRepository.findById(any())).thenReturn(Optional.of(new Character()));

        locationService.save(locationDto);

        verify(locationRepository).save(any(Location.class));
    }

    @Test
    void testGetAll() {
        List<Location> locations = Arrays.asList(new Location(), new Location());
        List<LocationDto> locationDtos = Arrays.asList(new LocationDto(), new LocationDto());
        when(locationRepository.findAll()).thenReturn(locations);
        when(mapper.entityListToDtoList(locations)).thenReturn(locationDtos);

        List<LocationDto> result = locationService.getAll();

        assertEquals(locationDtos, result);
    }

    @Test
    void testGetById() {
        Integer id = 1;
        Location location = new Location();
        LocationDto locationDto = new LocationDto();
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));
        when(mapper.entityToDto(location)).thenReturn(locationDto);

        LocationDto result = locationService.getById(id);

        assertEquals(locationDto, result);
    }

    @Test
    void testGetByIdNotFound() {
        Integer id = 1;
        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> locationService.getById(id));
    }

    @Test
    void testUpdate() {
        Integer id = 1;
        LocationDto locationDto = new LocationDto();
        locationDto.setResidents(Arrays.asList(new CharacterDto()));
        Location location = new Location();
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));
        when(characterRepository.findById(any())).thenReturn(Optional.of(new Character()));

        locationService.update(id, locationDto);

        verify(locationRepository, times(2)).save(location);
    }

    @Test
    void testDeleteById() {
        Integer id = 1;
        Location location = new Location();
        location.setOriginCharacters(new HashSet<>());
        location.setCurrentCharacters(new HashSet<>());
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        locationService.deleteById(id);

        verify(locationRepository).delete(location);
    }

    @Test
    void testRemoveCharacterFromLocation() {
        Integer locationId = 1;
        Integer characterId = 2;
        Location location = new Location();
        Character character = new Character();
        location.setCurrentCharacters(new HashSet<>(Collections.singletonList(character)));
        character.setLocation(location);

        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));
        when(characterRepository.findById(characterId)).thenReturn(Optional.of(character));

        locationService.removeCharacterFromLocation(locationId, characterId);

        verify(locationRepository).save(location);
        verify(characterRepository).save(character);
        assertTrue(location.getCurrentCharacters().isEmpty());
        assertNull(character.getLocation());
    }
}