package com.rick_morty.rick_morty_web_api.api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.exception.DataNotFoundException;
import com.rick_morty.rick_morty_web_api.api.mapper.EpisodeMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final RickAndMortyDbCataloger db;
    private final EpisodeMapper episodeMapper;

    @Transactional
    public void save(EpisodeDto episodeDto) {
        if (episodeDto != null) {
            db.getEpisodes().save(episodeMapper.dtoToEntity(episodeDto));
        }
    }

    public List<EpisodeDto> getAll() {
        return db.getEpisodes().findAll().stream()
                .map(episodeMapper::entityToDto)
                .toList();
    }

    public EpisodeDto getById(Integer id) {
        var episode = db.getEpisodes().findById(id);

        if (episode.isEmpty()) {
            throw new DataNotFoundException("Episode not found");
        }

        return episodeMapper.entityToDto(episode.get());
    }

    @Transactional
    public void update(Integer id, EpisodeDto episodeDto) {
        var episodeOptional = db.getEpisodes().findById(id);
        if (episodeOptional.isEmpty()) {
            throw new EntityNotFoundException("Episode not found");
        }
        var episode = episodeOptional.get();

        Set<Character> characters = new HashSet<>();

        for (CharacterSummaryDto characterDto : episodeDto.characters()) {
            Character character = db.getCharacters().findById(characterDto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Character not found"));
            characters.add(character);
        }

        episode.getCharacters().addAll(characters);

        episode.setName(episodeDto.title());
        episode.setEpisode(episodeDto.episode());
        episode.setAirDate(episodeDto.airDate());

        db.getEpisodes().save(episode);
    }

    @Transactional
    public void deleteById(Integer id) {
        var episode = db.getEpisodes().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found"));
        db.getEpisodes().delete(episode);
    }
}
