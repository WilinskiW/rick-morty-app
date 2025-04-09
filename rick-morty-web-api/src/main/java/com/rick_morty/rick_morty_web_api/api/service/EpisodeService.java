package com.rick_morty.rick_morty_web_api.api.service;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.repository.web.RickAndMortyDbCataloger;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.exception.DataNotFoundException;
import com.rick_morty.rick_morty_web_api.api.mapper.EpisodeMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final RickAndMortyDbCataloger db;
    private final EpisodeMapper mapper;

    @Transactional
    public void save(EpisodeDto episodeDto) {
        if(db.getEpisodes().existsByName(episodeDto.getTitle())) {
            throw new EntityExistsException("Character with this title already exists");
        }

        db.getEpisodes().save(mapper.dtoToEntity(episodeDto));
    }

    public Page<EpisodeDto> getAll(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber,  25);
        return mapper.entityListToDtoPage(db.getEpisodes().findAll(pageable));
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
            throw new DataNotFoundException("Episode not found");
        }
        var episode = episodeOptional.get();

        Set<Character> characters = new HashSet<>();

        for (CharacterDto characterDto : episodeDto.getCharacters()) {
            Character character = db.getCharacters().findById(characterDto.getId())
                    .orElseThrow(() -> new DataNotFoundException("Character not found"));
            characters.add(character);
        }

        episode.getCharacters().addAll(characters);

        episode.setName(episodeDto.getTitle());
        episode.setEpisode(episodeDto.getEpisode());
        episode.setAirDate(episodeDto.getAirDate());

        db.getEpisodes().save(episode);
    }

    @Transactional
    public void deleteById(Integer id) {
        var episode = db.getEpisodes().findById(id)
                .orElseThrow(() -> new DataNotFoundException("Episode not found"));
        db.getEpisodes().delete(episode);
    }

    @Transactional
    public void removeCharacterFromLocation(Integer episodeId, Integer characterId) {
        Episode episode = db.getEpisodes().findById(episodeId).orElseThrow(() -> new DataNotFoundException("Location not found"));
        Character character = db.getCharacters().findById(characterId).orElseThrow(() -> new DataNotFoundException("Character not found"));

        episode.getCharacters().remove(character);

        character.getEpisodes().remove(episode);

        db.getEpisodes().save(episode);
        db.getCharacters().save(character);
    }
}
