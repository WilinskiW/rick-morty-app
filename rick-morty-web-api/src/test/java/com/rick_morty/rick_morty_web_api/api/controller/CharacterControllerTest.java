package com.rick_morty.rick_morty_web_api.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.rick_morty.rick_morty_web_api.RickMortyWebApiApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CharacterControllerTest {

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
        characterDto.setStatus("Alive");
        characterDto.setSpecies("Human");
        characterDto.setGender("Male");
    }

    @Test
    @Order(1)
    void testFindAll() throws Exception {
        Page<CharacterDto> characters = Page.empty(PageRequest.of(0, 10));
        when(characterService.getAll(1)).thenReturn(characters);

        mockMvc.perform(get("/api/characters?page=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(characters)));

        verify(characterService, times(1)).getAll(1);
    }

    @Test
    void testCreateCharacter() throws Exception {
        doNothing().when(characterService).save(any(CharacterDto.class));

        mockMvc.perform(post("/api/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterDto)))
                .andExpect(status().isOk());

        verify(characterService, times(1)).save(any(CharacterDto.class));
    }

    @Test
    void testCreateCharacterWhenValidationError() throws Exception {
        characterDto.setName("");

        mockMvc.perform(post("/api/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterDto)))
                .andExpect(status().isBadRequest());

        verify(characterService, times(0)).save(any(CharacterDto.class));
    }

    @Test
    void testFindById() throws Exception {
        when(characterService.getCharacterById(1)).thenReturn(characterDto);

        mockMvc.perform(get("/api/characters/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(characterDto)));

        verify(characterService, times(1)).getCharacterById(1);
    }

    @Test
    void testFindCharactersNotInLocation() throws Exception {
        List<CharacterDto> characters = List.of(characterDto);
        when(characterService.getAllNotInTheLocation(1)).thenReturn(characters);

        mockMvc.perform(get("/api/characters/1/notInLocation"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(characters)));

        verify(characterService, times(1)).getAllNotInTheLocation(1);
    }

    @Test
    void testFindCharactersNotInEpisode() throws Exception {
        List<CharacterDto> characters = List.of(characterDto);
        when(characterService.getAllNotInTheLocation(1)).thenReturn(characters); // Twoja metoda ma tą samą implementację

        mockMvc.perform(get("/api/characters/1/notInEpisode"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(characters)));

        verify(characterService, times(1)).getAllNotInTheLocation(1);
    }

    @Test
    void testUpdateCharacter() throws Exception {
        doNothing().when(characterService).update(eq(1), any(CharacterDto.class));

        mockMvc.perform(put("/api/characters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterDto)))
                .andExpect(status().isOk());

        verify(characterService, times(1)).update(eq(1), any(CharacterDto.class));
    }

    @Test
    void testUpdateCharacterWhenValidationError() throws Exception {
        characterDto.setName("");

        mockMvc.perform(put("/api/characters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterDto)))
                .andExpect(status().isBadRequest());

        verify(characterService, times(0)).update(eq(1), any(CharacterDto.class));
    }

    @Test
    void testDeleteCharacter() throws Exception {
        doNothing().when(characterService).deleteById(1);

        mockMvc.perform(delete("/api/characters/1"))
                .andExpect(status().isOk());

        verify(characterService, times(1)).deleteById(1);
    }
}
