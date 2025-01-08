package com.rick_morty.rick_morty_updater.updater;

public interface DbUpdater {
    default void updateDatabase() {
        updateCharacters();
        updateLocations();
        updateEpisodes();
    }

    void updateCharacters();

    void updateLocations();

    void updateEpisodes();
}
