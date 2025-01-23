package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import com.rick_morty.rick_morty_web_api.api.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ViewCharacterControllerTest {

    @Mock
    private CharacterService characterService;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private ViewCharacterController viewCharacterController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(viewCharacterController).build();
    }

    @Test
    void testShowSinglePage() throws Exception {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setId(1);
        characterDto.setName("Rick Sanchez");

        when(characterService.getCharacterById(1)).thenReturn(characterDto);

        mockMvc.perform(get("/character/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("character/character"))
                .andExpect(model().attributeExists("character"))
                .andExpect(model().attribute("character", characterDto));

        verify(characterService).getCharacterById(1);
    }

    @Test
    void testShowScheduleCharacterPage() throws Exception {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setId(1);
        characterDto.setName("Rick Sanchez");

        when(characterService.getScheduleCharacter()).thenReturn(characterDto);

        mockMvc.perform(get("/character/schedule"))
                .andExpect(status().isOk())
                .andExpect(view().name("character/character"))
                .andExpect(model().attributeExists("character"))
                .andExpect(model().attribute("character", characterDto));

        verify(characterService).getScheduleCharacter();
    }

    @Test
    void testShowAll() throws Exception {
        List<CharacterDto> characters = Arrays.asList(
                new CharacterDto(),
                new CharacterDto()
        );

        when(characterService.getAll()).thenReturn(characters);

        mockMvc.perform(get("/character/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("character/characters"))
                .andExpect(model().attributeExists("characters"))
                .andExpect(model().attribute("characters", characters));

        verify(characterService).getAll();
    }

    @Test
    void testShowEditPage() throws Exception {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setId(1);
        characterDto.setName("Rick Sanchez");

        List<LocationDto> locations = Arrays.asList(
                new LocationDto(),
                new LocationDto()
        );

        when(characterService.getCharacterById(1)).thenReturn(characterDto);
        when(locationService.getAll()).thenReturn(locations);

        mockMvc.perform(get("/character/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("character/edit-character"))
                .andExpect(model().attributeExists("character"))
                .andExpect(model().attribute("character", characterDto))
                .andExpect(model().attributeExists("locations"))
                .andExpect(model().attribute("locations", locations));

        verify(characterService).getCharacterById(1);
        verify(locationService).getAll();
    }

    @Test
    void testShowAddPage() throws Exception {
        List<LocationDto> locations = Arrays.asList(
                new LocationDto(),
                new LocationDto()
        );

        when(locationService.getAll()).thenReturn(locations);

        mockMvc.perform(get("/character/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("character/add-character"))
                .andExpect(model().attributeExists("character"))
                .andExpect(model().attributeExists("locations"))
                .andExpect(model().attribute("locations", locations));

        verify(locationService).getAll();
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/character/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/character/all"));

        verify(characterService).deleteById(1);
    }

    @Test
    void testUpdate() throws Exception {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setId(1);
        characterDto.setName("Rick Sanchez");

        mockMvc.perform(post("/character/edit/1")
                        .flashAttr("characterDto", characterDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/character/all"));

        verify(characterService).update(1, characterDto);
    }

    @Test
    void testCreate() throws Exception {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setName("Rick Sanchez");

        mockMvc.perform(post("/character/add")
                        .flashAttr("characterDto", characterDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/character/all"));

        verify(characterService).save(characterDto);
    }
}