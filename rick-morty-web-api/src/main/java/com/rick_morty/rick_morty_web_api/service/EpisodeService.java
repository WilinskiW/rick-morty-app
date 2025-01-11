package com.rick_morty.rick_morty_web_api.service;

import com.rick_morty.rick_morty_data.repository.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.exception.DataNotFoundException;
import com.rick_morty.rick_morty_web_api.mapper.EpisodeMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final RickAndMortyDbCataloger db;
    private final EpisodeMapper mapper;

    @Transactional
    public void save(EpisodeDto episodeDto) {
        if (episodeDto != null) {
            db.getEpisodes().save(mapper.dtoToEntity(episodeDto));
        }
    }

    public List<EpisodeDto> getAll() {
        return db.getEpisodes().findAll().stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public EpisodeDto getById(Integer id) {
        var episode = db.getEpisodes().findById(id);

        if (episode.isEmpty()) {
            throw new DataNotFoundException("Episode not found");
        }

        return mapper.entityToDto(episode.get());
    }

    @Transactional
    public void update(Integer id, EpisodeDto episodeDto) {
        var episodeOptional = db.getEpisodes().findById(id);
        if (episodeOptional.isEmpty()) {
            throw new EntityNotFoundException("Episode not found");
        }
        var episode = episodeOptional.get();

        episode.setName(episodeDto.title());
        episode.setEpisode(episode.getEpisode());
        episode.setAirDate(episode.getAirDate());
        episode.setCreated(episode.getCreated());
        episode.setCharacters(episode.getCharacters());

        db.getEpisodes().save(episode);
    }

    @Transactional
    public void deleteById(Integer id) {
        var episode = db.getEpisodes().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found"));
        db.getEpisodes().delete(episode);
    }
}
