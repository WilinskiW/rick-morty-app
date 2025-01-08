package com.rick_morty.rick_morty_updater.updater;

import org.springframework.scheduling.annotation.Async;

public interface DbUpdater {
    @Async
    default void updateDatabase() {
        updateCharacters();
        updateLocations();
        updateEpisodes();
    }

    void updateCharacters();

    void updateLocations();

    void updateEpisodes();
}
