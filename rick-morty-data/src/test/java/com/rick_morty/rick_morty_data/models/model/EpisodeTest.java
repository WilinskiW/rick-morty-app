package com.rick_morty.rick_morty_data.models.model;

import com.rick_morty.rick_morty_data.model.Episode;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpisodeTest {
    @Test
    public void createdShouldBeInitializedToNow() {
        Episode episode  = new Episode();
        assertThat(episode.getCreated()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    public void createdShouldHaveHashSetInCharactersTest() {
        Episode episode = new Episode();
        assertEquals(new HashSet<>(), episode.getCharacters());
    }
}
