package com.rick_morty.rick_morty_data.models.model;

import com.rick_morty.rick_morty_data.model.Location;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {
    @Test
    public void createdShouldBeInitializedToNow() {
        Location location = new Location();
        assertThat(location.getCreated()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    public void createdShouldHaveHashSetInOriginAndCurrentTest() {
        Location location = new Location();
        assertEquals(new HashSet<>(), location.getOriginCharacters());
        assertEquals(new HashSet<>(), location.getCurrentCharacters());
    }
}
