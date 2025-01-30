package com.rick_morty.rick_morty_data.models.repository;

import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.repository.web.EpisodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Profile("test")
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
}
