package com.rick_morty.rick_morty_web_api.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
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
public class CharacterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterService characterService;

    @Autowired
    private ObjectMapper objectMapper;

    private CharacterDto characterDto;

    @BeforeEach
    void setUp() {
        characterDto = new CharacterDto();
        characterDto.setId(1);
        characterDto.setName("Rick Sanchez");
    }

    @Test
    @Order(1)
    void testFindAll() throws Exception {
        CharacterDto characterDto2 = new CharacterDto();
        characterDto.setId(2);
        characterDto.setName("Morty Smith");

        List<CharacterDto> characters = List.of(characterDto, characterDto2);
        when(characterService.getAll()).thenReturn(characters);

        mockMvc.perform(get("/api/character/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(characters)));

        verify(characterService, times(1)).getAll();
    }

    @Test
    void testCreateCharacter() throws Exception {
        doNothing().when(characterService).save(any(CharacterDto.class));

        mockMvc.perform(post("/api/character")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterDto)))
                .andExpect(status().isOk());

        verify(characterService, times(1)).save(any(CharacterDto.class));
    }

    @Test
    void testFindById() throws Exception {
        when(characterService.getCharacterById(1)).thenReturn(characterDto);

        mockMvc.perform(get("/api/character/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(characterDto)));

        verify(characterService, times(1)).getCharacterById(1);
    }

    @Test
    void testFindByNameLike() throws Exception {
        List<CharacterDto> characters = Arrays.asList(characterDto);
        when(characterService.getAllLikeName("Rick")).thenReturn(characters);

        mockMvc.perform(get("/api/character/like/Rick"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(characters)));

        verify(characterService, times(1)).getAllLikeName("Rick");
    }

    @Test
    void testFindScheduleCharacter() throws Exception {
        when(characterService.getScheduleCharacter()).thenReturn(characterDto);

        mockMvc.perform(get("/api/character/schedule"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(characterDto)));

        verify(characterService, times(1)).getScheduleCharacter();
    }

    @Test
    void testUpdateCharacter() throws Exception {
        doNothing().when(characterService).update(eq(1), any(CharacterDto.class));

        mockMvc.perform(put("/api/character/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterDto)))
                .andExpect(status().isOk());

        verify(characterService, times(1)).update(eq(1), any(CharacterDto.class));
    }

    @Test
    void testDeleteCharacter() throws Exception {
        doNothing().when(characterService).deleteById(1);

        mockMvc.perform(delete("/api/character/1"))
                .andExpect(status().isOk());

        verify(characterService, times(1)).deleteById(1);
    }
}