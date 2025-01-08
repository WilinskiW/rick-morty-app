package com.rick_morty.rick_morty_updater.updater;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RickAndMortyDataUpdater implements DbUpdater{
    @Override
    public void updateCharacters() {

    }

    @Override
    public void updateLocations() {

    }

    @Override
    public void updateEpisodes() {

    }
}
