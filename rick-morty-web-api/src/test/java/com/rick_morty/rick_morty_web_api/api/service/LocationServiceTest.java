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
        locationDto.setName("Earth");
        CharacterDto residentDto = new CharacterDto();
        residentDto.setId(1);
        locationDto.setResidents(Collections.singletonList(residentDto));

        when(locationRepository.existsByName(locationDto.getName())).thenReturn(false);
        when(characterRepository.findById(any())).thenReturn(Optional.of(new Character()));

        locationService.save(locationDto);

        verify(locationRepository).save(any(Location.class));
    }

    @Test
    void testSaveWhenLocationAlreadyExists() {
        LocationDto locationDto = new LocationDto();
        locationDto.setName("Earth 241");

        when(locationRepository.existsByName(locationDto.getName())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> locationService.save(locationDto));
    }

    @Test
    void testGetAll() {
        List<Location> locations = Arrays.asList(new Location(), new Location());
        Page<Location> page = new PageImpl<>(locations, PageRequest.of(0, 25), locations.size());
        Page<LocationDto> dtoPage = new PageImpl<>(Arrays.asList(new LocationDto(), new LocationDto()), PageRequest.of(0, 25), locations.size());

        when(locationRepository.findAll(PageRequest.of(0, 25))).thenReturn(page);
        when(mapper.entityListToDtoPage(page)).thenReturn(dtoPage);

        Page<LocationDto> result = locationService.getAll(0);

        assertEquals(dtoPage, result);
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
        locationDto.setName("New Earth");
        CharacterDto residentDto = new CharacterDto();
        residentDto.setId(1);
        locationDto.setResidents(Collections.singletonList(residentDto));

        Location location = new Location();
        location.setCurrentCharacters(new HashSet<>());

        Character character = new Character();
        character.setId(1);

        when(locationRepository.findById(id)).thenReturn(Optional.of(location));
        when(characterRepository.findById(residentDto.getId())).thenReturn(Optional.of(character));
        when(locationRepository.findById(id)).thenReturn(Optional.of(location)); // For removeCharacterFromLocation
        when(characterRepository.findById(residentDto.getId())).thenReturn(Optional.of(character)); // For removeCharacterFromLocation

        locationService.update(id, locationDto);

        verify(locationRepository, atLeastOnce()).save(location);
        verify(characterRepository, atLeastOnce()).save(character);
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
