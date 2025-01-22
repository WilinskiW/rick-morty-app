package com.rick_morty.rick_morty_data.models.model;

import com.rick_morty.rick_morty_data.model.Character;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterTest {
    @Test
    public void createdShouldBeInitializedToNow() {
        Character character = new Character();
        assertThat(character.getCreated()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    public void createdShouldHaveHashSetInEpisodesTest() {
        Character character = new Character();
        assertEquals(new HashSet<>(), character.getEpisodes());
    }
}
