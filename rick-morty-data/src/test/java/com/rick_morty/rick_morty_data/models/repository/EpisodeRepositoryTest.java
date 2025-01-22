package com.rick_morty.rick_morty_data.models.repository;

import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.repository.EpisodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class EpisodeRepositoryTest {
    @Autowired
    private EpisodeRepository episodeRepository;

    @Test
    void shouldSaveAndFindEpisodeById() {
        Episode episode = new Episode();
        episode.setSourceId(1);
        episode.setName("Pilot");
        episode.setAirDate("December 2, 2013");
        episode.setEpisode("S01E01");

        episodeRepository.save(episode);

        boolean exists = episodeRepository.existsBySourceId(1);
        assertThat(exists).isTrue();
    }

    @Test
    void throwExceptionWhenNameIsNull() {
        Episode episode = new Episode();
        episode.setSourceId(1);
        episode.setAirDate("December 2, 2013");
        episode.setEpisode("S01E01");

        assertThrows(DataIntegrityViolationException.class, () -> episodeRepository.save(episode));
    }

    @Test
    void throwExceptionWhenAirDateIsNull() {
        Episode episode = new Episode();
        episode.setSourceId(1);
        episode.setName("Pilot");
        episode.setEpisode("S01E01");

        assertThrows(DataIntegrityViolationException.class, () -> episodeRepository.save(episode));
    }

    @Test
    void throwExceptionWhenEpisodeIsNull() {
        Episode episode = new Episode();
        episode.setSourceId(1);
        episode.setName("Pilot");
        episode.setAirDate("December 2, 2013");

        assertThrows(DataIntegrityViolationException.class, () -> episodeRepository.save(episode));
    }
}
