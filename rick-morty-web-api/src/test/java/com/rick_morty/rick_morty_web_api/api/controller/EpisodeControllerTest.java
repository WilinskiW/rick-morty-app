package com.rick_morty.rick_morty_web_api.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.service.EpisodeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.rick_morty.rick_morty_web_api.RickMortyWebApiApplication.class)
@AutoConfigureMockMvc(addFilters = false)
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
        Page<EpisodeDto> episodes = Page.empty(PageRequest.of(0, 10));
        when(episodeService.getAll(1)).thenReturn(episodes);

        mockMvc.perform(get("/api/episodes?page=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(episodes)));

        verify(episodeService, times(1)).getAll(1);
    }

    @Test
    void testCreateEpisode() throws Exception {
        doNothing().when(episodeService).save(any(EpisodeDto.class));

        mockMvc.perform(post("/api/episodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(episodeDto)))
                .andExpect(status().isOk());

        verify(episodeService, times(1)).save(any(EpisodeDto.class));
    }

    @Test
    void testCreateEpisodeWhenValidationError() throws Exception {
        episodeDto.setTitle("");

        mockMvc.perform(post("/api/episodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(episodeDto)))
                .andExpect(status().isBadRequest());

        verify(episodeService, times(0)).save(any(EpisodeDto.class));
    }

    @Test
    @Order(3)
    void testFindById() throws Exception {
        when(episodeService.getById(1)).thenReturn(episodeDto);

        mockMvc.perform(get("/api/episodes/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(episodeDto)));

        verify(episodeService, times(1)).getById(1);
    }

    @Test
    void testUpdateEpisode() throws Exception {
        doNothing().when(episodeService).update(eq(1), any(EpisodeDto.class));

        mockMvc.perform(put("/api/episodes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(episodeDto)))
                .andExpect(status().isOk());

        verify(episodeService, times(1)).update(1, episodeDto);
    }

    @Test
    void testUpdateEpisodeWhenValidationError() throws Exception {
        episodeDto.setTitle("");
        doNothing().when(episodeService).update(eq(1), any(EpisodeDto.class));

        mockMvc.perform(put("/api/episodes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(episodeDto)))
                .andExpect(status().isBadRequest());

        verify(episodeService, times(0)).update(eq(1), any(EpisodeDto.class));
    }

    @Test
    void testDeleteEpisode() throws Exception {
        doNothing().when(episodeService).deleteById(1);

        mockMvc.perform(delete("/api/episodes/1"))
                .andExpect(status().isOk());

        verify(episodeService, times(1)).deleteById(1);
    }

    @Test
    void testRemoveCharacterFromEpisode() throws Exception {
        doNothing().when(episodeService).removeCharacterFromLocation(1, 2);

        mockMvc.perform(delete("/api/episodes/1/remove-character/2"))
                .andExpect(status().isOk());

        verify(episodeService, times(1)).removeCharacterFromLocation(1, 2);
    }
}