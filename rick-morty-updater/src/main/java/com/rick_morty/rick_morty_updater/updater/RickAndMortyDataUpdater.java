package com.rick_morty.rick_morty_updater.updater;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_data_client.RickAndMortyDataPuller;
import com.rick_morty.rick_morty_data_client.contract.CharacterDto;
import com.rick_morty.rick_morty_updater.mapper.CatalogMapper;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<CharacterDto> characterDtos = getCharactersDto();
        List<Long> sourceIds = characterDtos.stream()
                .map(dto -> (long) dto.id())
                .toList();

        List<Long> existingSourceIds = dbCataloger.getCharacters()
                .withSourceIds(sourceIds)
                .stream()
                .map(character -> (long) character.getSource_id() )
                .toList();

        List<Character> toAddCharacters = characterDtos.stream()
                .filter(dto -> !existingSourceIds.contains((long) dto.id()))
                .map(dto -> entityMapper.forCharacter().mapDtoToEntity(dto))
                .toList();

        if (!toAddCharacters.isEmpty()) {
            dbCataloger.getCharacters().saveAll(toAddCharacters);
        }
    }

    private List<CharacterDto> getCharactersDto() {
        int totalPages = dataPuller.getCharacters().info().pages();
        List<CharacterDto> allCharacterDtos = new ArrayList<>();
        for (int page = 1; page <= totalPages; page++) {
            allCharacterDtos.addAll(dataPuller.getCharacters(page).results());
        }
        return allCharacterDtos;
    }


    @Override
    @Transactional
    public void updateLocations() {
        List<Location> locations = dataPuller.getLocations().results().stream()
                .map(locationDto ->
                        entityMapper.forLocation().mapDtoToEntity(locationDto))
                .toList();
        System.out.println(locations);
    }

    @Override
    @Transactional
    public void updateEpisodes() {
        List<Episode> episodes = dataPuller.getEpisodes().results().stream()
                .map(episodeDto ->
                        entityMapper.forEpisode().mapDtoToEntity(episodeDto))
                .toList();
        dbCataloger.getEpisodes().saveAll(episodes);
    }
}
