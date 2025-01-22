package com.rick_morty.rick_morty_updater;

import com.rick_morty.rick_morty_updater.controller.UpdaterController;
import com.rick_morty.rick_morty_updater.updater.RickAndMortyDataUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdaterControllerTest {

    @Mock
    private RickAndMortyDataUpdater dbUpdater;

    private UpdaterController updaterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updaterController = new UpdaterController(dbUpdater);
    }

    @Test
    void testStartUpdate() {
        doNothing().when(dbUpdater).syncData();

        ResponseEntity<String> response = updaterController.startUpdate();

        verify(dbUpdater, times(1)).syncData();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().startsWith("Updater started..."));
        assertNotNull(LocalDateTime.parse(response.getBody().substring(18)));
    }

    @Test
    void testStartUpdateWithException() {
        doThrow(new RuntimeException("Test exception")).when(dbUpdater).syncData();

        assertThrows(RuntimeException.class, () -> updaterController.startUpdate());
        verify(dbUpdater, times(1)).syncData();
    }

    @Test
    void testConstructor() {
        UpdaterController controller = new UpdaterController(dbUpdater);

        assertNotNull(controller);
    }
}
