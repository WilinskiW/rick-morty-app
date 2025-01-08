package com.rick_morty.rick_morty_updater.updater;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_data_client.RickAndMortyDataPuller;
import com.rick_morty.rick_morty_updater.mapper.CatalogMapper;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RickAndMortyDataUpdater implements DbUpdater {
    private final RickAndMortyDataPuller dataPuller;
    private final CatalogMapper entityMapper;
    private final RickAndMortyDbCataloger dbCataloger;

    public RickAndMortyDataUpdater(RickAndMortyDataPuller dataPuller, CatalogMapper entityMapper, RickAndMortyDbCataloger dbCataloger) {
        this.dataPuller = dataPuller;
        this.entityMapper = entityMapper;
        this.dbCataloger = dbCataloger;
    }

    @Async
    @Override
    public void updateDatabase() {
        DbUpdater.super.updateDatabase();
    }

    @Override
    @Transactional
    public void updateCharacters() {
        List<Character> characters = dataPuller.getCharacters().results().stream()
                .map(characterDto ->
                        entityMapper.forCharacter().mapDtoToEntity(characterDto))
                .toList();
        dbCataloger.getCharacterRepository().saveAll(characters);
        System.out.println(characters);
        System.out.println("Done!");
    }

    @Override
    @Transactional
    public void updateLocations() {
        List<Location> locations = dataPuller.getLocations().results().stream()
                .map(locationDto ->
                        entityMapper.forLocation().mapDtoToEntity(locationDto))
                .toList();
        System.out.println(locations);
        System.out.println("Done!");
        dbCataloger.getLocationRepository().saveAll(locations);
    }

    @Override
    @Transactional
    public void updateEpisodes() {
        List<Episode> episodes = dataPuller.getEpisodes().results().stream()
                .map(episodeDto ->
                        entityMapper.forEpisode().mapDtoToEntity(episodeDto))
                .toList();
        dbCataloger.getEpisodeRepository().saveAll(episodes);
        System.out.println(episodes);
        System.out.println("Done!");
    }
}
