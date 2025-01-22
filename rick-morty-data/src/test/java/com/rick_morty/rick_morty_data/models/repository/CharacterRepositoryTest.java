package com.rick_morty.rick_morty_data.models.repository;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.model.Episode;
import com.rick_morty.rick_morty_data.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CharacterRepositoryTest {

    @Autowired
    private CharacterRepository characterRepository;

    @Test
    void shouldSaveAndFindCharacterTest() {
        Character character = new Character();
        character.setSourceId(1);
        character.setName("Rick Sanchez");
        character.setStatus("Alive");
        character.setSpecies("Human");
        character.setGender("Male");

        characterRepository.save(character);

        List<Character> characters = characterRepository.findLike("Rick%");
        assertEquals(1, characters.size());
        assertEquals("Rick Sanchez", characters.getFirst().getName());
    }

    @Test
    void shouldCheckIfCharacterExistsBySourceIdTest() {
        Character character = new Character();
        character.setSourceId(2);
        character.setName("Morty Smith");
        character.setStatus("Alive");
        character.setSpecies("Human");
        character.setGender("Male");

        characterRepository.save(character);

        boolean exists = characterRepository.existsBySourceId(2);
        assertTrue(exists);
    }

    @Test
    public void checkDataIntegrityWhenNameIsNullTest(){
        Character character = new Character();
        character.setStatus("Alive");
        character.setSpecies("Human");
        character.setGender("Male");

        assertThrows(DataIntegrityViolationException.class, () -> characterRepository.save(character));

        characterRepository.save(character);
    }

    @Test
    public void checkDataIntegrityWhenStatusIsNullTest(){
        Character character = new Character();
        character.setSourceId(2);
        character.setName("Morty Smith");
        character.setSpecies("Human");
        character.setGender("Male");

        assertThrows(DataIntegrityViolationException.class, () -> characterRepository.save(character));

        characterRepository.save(character);
    }

    @Test
    public void checkDataIntegrityWhenSpeciesIsNullTest(){
        Character character = new Character();
        character.setName("Morty Smith");
        character.setStatus("Alive");
        character.setGender("Male");

        assertThrows(DataIntegrityViolationException.class, () -> characterRepository.save(character));

        characterRepository.save(character);
    }

    @Test
    public void checkDataIntegrityWhenGenderIsNullTest(){
        Character character = new Character();
        character.setName("Morty Smith");
        character.setStatus("Alive");
        character.setSpecies("Human");

        assertThrows(DataIntegrityViolationException.class, () -> characterRepository.save(character));

        characterRepository.save(character);
    }

    @Test
    void shouldSaveCharacterWithEpisodes() {
        Episode episode = new Episode();
        episode.setSourceId(101);
        episode.setName("Pilot");
        episode.setAirDate("December 2, 2013");
        episode.setEpisode("S01E01");

        Character character = new Character();
        character.setSourceId(1);
        character.setName("Rick Sanchez");
        character.setStatus("Alive");
        character.setSpecies("Human");
        character.setGender("Male");

        character.getEpisodes().add(episode);

        characterRepository.save(character);

        Character retrievedCharacter = characterRepository.findById(character.getId()).orElseThrow();
        assertEquals(1, retrievedCharacter.getEpisodes().size());
        assertThat(retrievedCharacter.getEpisodes().iterator().next().getName()).isEqualTo("Pilot");
    }
}
