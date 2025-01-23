package com.rick_morty.rick_morty_ui.controller;

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

class ViewLocationControllerTest {

    @Mock
    private LocationService locationService;

    @Mock
    private CharacterService characterService;

    @InjectMocks
    private ViewLocationController viewLocationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(viewLocationController).build();
    }

    @Test
    void testShowSinglePage() throws Exception {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(1);

        when(locationService.getById(1)).thenReturn(locationDto);

        mockMvc.perform(get("/location/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("location/location"))
                .andExpect(model().attributeExists("location"));

        verify(locationService).getById(1);
    }

    @Test
    void testShowAll() throws Exception {
        List<LocationDto> locations = Arrays.asList(new LocationDto(), new LocationDto());
        when(locationService.getAll()).thenReturn(locations);

        mockMvc.perform(get("/location/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("location/locations"))
                .andExpect(model().attributeExists("locations"));

        verify(locationService).getAll();
    }

    @Test
    void testShowEditPage() throws Exception {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(1);

        when(locationService.getById(1)).thenReturn(locationDto);
        when(characterService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/location/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("location/edit-location"))
                .andExpect(model().attributeExists("location", "allCharacters"));

        verify(locationService).getById(1);
        verify(characterService).getAll();
    }

    @Test
    void testShowAddPage() throws Exception {
        when(characterService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/location/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("location/add-location"))
                .andExpect(model().attributeExists("location", "allCharacters"));

        verify(characterService).getAll();
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/location/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/location/all"));

        verify(locationService).deleteById(1);
    }

    @Test
    void testRemoveCharacter() throws Exception {
        mockMvc.perform(post("/location/1/edit/remove-character/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/location/edit/1"));

        verify(locationService).removeCharacterFromLocation(1, 2);
    }

    @Test
    void testUpdate() throws Exception {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(1);

        mockMvc.perform(post("/location/edit/1")
                        .flashAttr("locationDto", locationDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/location/all"));

        verify(locationService).update(1, locationDto);
    }

    @Test
    void testCreate() throws Exception {
        LocationDto locationDto = new LocationDto();

        mockMvc.perform(post("/location/add")
                        .flashAttr("locationDto", locationDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/location/all"));

        verify(locationService).save(locationDto);
    }
}