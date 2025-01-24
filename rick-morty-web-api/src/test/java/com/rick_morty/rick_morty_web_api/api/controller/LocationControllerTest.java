package com.rick_morty.rick_morty_web_api.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.service.LocationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.rick_morty.rick_morty_web_api.RickMortyWebApiApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @Autowired
    private ObjectMapper objectMapper;

    private LocationDto locationDto;

    @BeforeEach
    void setUp() {
        locationDto = new LocationDto();
        locationDto.setId(1);
        locationDto.setName("Earth");
        locationDto.setType("Planet");
        locationDto.setDimension("Dimension C-137");
    }

    @Test
    @Order(1)
    void testFindAll() throws Exception {
        List<LocationDto> locations = Arrays.asList(locationDto);
        when(locationService.getAll()).thenReturn(locations);

        mockMvc.perform(get("/api/location/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(locations)));

        verify(locationService, times(1)).getAll();
    }

    @Test
    @Order(2)
    void testCreateLocation() throws Exception {
        doNothing().when(locationService).save(any(LocationDto.class));

        mockMvc.perform(post("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto)))
                .andExpect(status().isOk());

        verify(locationService, times(1)).save(any(LocationDto.class));
    }

    @Test
    void testCreateLocationWhenValidationError() throws Exception {
        locationDto.setName("");
        mockMvc.perform(post("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        verify(locationService, times(0)).save(any(LocationDto.class));
    }

    @Test
    @Order(3)
    void testFindById() throws Exception {
        when(locationService.getById(1)).thenReturn(locationDto);

        mockMvc.perform(get("/api/location/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(locationDto)));

        verify(locationService, times(1)).getById(1);
    }

    @Test
    @Order(4)
    void testUpdateLocation() throws Exception {
        doNothing().when(locationService).update(eq(1), any(LocationDto.class));

        mockMvc.perform(put("/api/location/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto)))
                .andExpect(status().isOk());

        verify(locationService, times(1)).update(eq(1), any(LocationDto.class));
    }

    @Test
    void testUpdateLocationWhenValidationError() throws Exception {
        locationDto.setName("");

        doNothing().when(locationService).update(eq(1), any(LocationDto.class));

        mockMvc.perform(put("/api/location/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        verify(locationService, times(0)).update(eq(1), any(LocationDto.class));
    }

    @Test
    @Order(5)
    void testDeleteLocation() throws Exception {
        doNothing().when(locationService).deleteById(1);

        mockMvc.perform(delete("/api/location/1"))
                .andExpect(status().isOk());

        verify(locationService, times(1)).deleteById(1);
    }

    @Test
    @Order(6)
    void testRemoveCharacterFromLocation() throws Exception {
        doNothing().when(locationService).removeCharacterFromLocation(1, 2);

        mockMvc.perform(delete("/api/location/1/remove-character/2"))
                .andExpect(status().isOk());

        verify(locationService, times(1)).removeCharacterFromLocation(1, 2);
    }
}