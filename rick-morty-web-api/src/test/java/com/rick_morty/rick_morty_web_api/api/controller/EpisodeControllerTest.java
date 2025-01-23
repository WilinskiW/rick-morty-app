package com.rick_morty.rick_morty_web_api.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.service.EpisodeService;
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
@AutoConfigureMockMvc()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EpisodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EpisodeService episodeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EpisodeDto episodeDto;

    @BeforeEach
    void setUp() {
        episodeDto = new EpisodeDto();
        episodeDto.setId(1);
        episodeDto.setTitle("Pilot");
        episodeDto.setAirDate("December 2, 2013");
        episodeDto.setEpisode("S01E01");
    }

    @Test
    @Order(1)
    void testFindAll() throws Exception {
        List<EpisodeDto> episodes = Arrays.asList(episodeDto);
        when(episodeService.getAll()).thenReturn(episodes);

        mockMvc.perform(get("/api/episode/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(episodes)));

        verify(episodeService, times(1)).getAll();
    }

    @Test
    void testCreateEpisode() throws Exception {
        doNothing().when(episodeService).save(any(EpisodeDto.class));

        mockMvc.perform(post("/api/episode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(episodeDto)))
                .andExpect(status().isOk());

        verify(episodeService, times(1)).save(any(EpisodeDto.class));
    }

    @Test
    @Order(3)
    void testFindById() throws Exception {
        when(episodeService.getById(1)).thenReturn(episodeDto);

        mockMvc.perform(get("/api/episode/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(episodeDto)));

        verify(episodeService, times(1)).getById(1);
    }

    @Test
    void testUpdateEpisode() throws Exception {
        doNothing().when(episodeService).update(eq(1), any(EpisodeDto.class));

        mockMvc.perform(put("/api/episode/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(episodeDto)))
                .andExpect(status().isOk());

        verify(episodeService, times(1)).update(eq(1), any(EpisodeDto.class));
    }

    @Test
    void testDeleteEpisode() throws Exception {
        doNothing().when(episodeService).deleteById(1);

        mockMvc.perform(delete("/api/episode/1"))
                .andExpect(status().isOk());

        verify(episodeService, times(1)).deleteById(1);
    }

    @Test
    void testRemoveCharacterFromEpisode() throws Exception {
        doNothing().when(episodeService).removeCharacterFromLocation(1, 2);

        mockMvc.perform(delete("/api/episode/1/remove-character/2"))
                .andExpect(status().isOk());

        verify(episodeService, times(1)).removeCharacterFromLocation(1, 2);
    }
}