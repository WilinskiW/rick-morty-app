package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import com.rick_morty.rick_morty_web_api.api.service.EpisodeService;
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

class ViewEpisodeControllerTest {

    @Mock
    private EpisodeService episodeService;

    @Mock
    private CharacterService characterService;

    @InjectMocks
    private ViewEpisodeController viewEpisodeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(viewEpisodeController).build();
    }

    @Test
    void testShowSinglePage() throws Exception {
        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setId(1);
        episodeDto.setEpisode("S01E01");
        episodeDto.setCharacters(List.of(new CharacterDto(), new CharacterDto()));

        when(episodeService.getById(1)).thenReturn(episodeDto);

        mockMvc.perform(get("/episode/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("episode/episode"))
                .andExpect(model().attributeExists("episode", "title", "characters"));

        verify(episodeService).getById(1);
    }

    @Test
    void testShowAll() throws Exception {
        List<EpisodeDto> episodes = Arrays.asList(new EpisodeDto(), new EpisodeDto());
        when(episodeService.getAll()).thenReturn(episodes);

        mockMvc.perform(get("/episode/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("episode/episodes"))
                .andExpect(model().attributeExists("episodes"));

        verify(episodeService).getAll();
    }

    @Test
    void testShowEditPage() throws Exception {
        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setId(1);
        episodeDto.setCharacters(List.of(new CharacterDto(), new CharacterDto()));

        when(episodeService.getById(1)).thenReturn(episodeDto);
        when(characterService.getAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/episode/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("episode/edit-episode"))
                .andExpect(model().attributeExists("episode", "characters", "allCharacters"));

        verify(episodeService).getById(1);
        verify(characterService).getAll();
    }

    @Test
    void testShowAddPage() throws Exception {
        when(characterService.getAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/episode/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("episode/add-episode"))
                .andExpect(model().attributeExists("episode", "allCharacters"));

        verify(characterService).getAll();
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/episode/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/episode/all"));

        verify(episodeService).deleteById(1);
    }

    @Test
    void testRemoveCharacter() throws Exception {
        mockMvc.perform(post("/episode/1/edit/remove-character/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/episode/edit/1"));

        verify(episodeService).removeCharacterFromLocation(1, 2);
    }

    @Test
    void testUpdate() throws Exception {
        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setId(1);

        mockMvc.perform(post("/episode/edit/1")
                        .flashAttr("episodeDto", episodeDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/episode/1"));

        verify(episodeService).update(1, episodeDto);
    }

    @Test
    void testCreate() throws Exception {
        EpisodeDto episodeDto = new EpisodeDto();

        mockMvc.perform(post("/episode/add")
                        .flashAttr("episodeDto", episodeDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/episode/all"));

        verify(episodeService).save(episodeDto);
    }
}