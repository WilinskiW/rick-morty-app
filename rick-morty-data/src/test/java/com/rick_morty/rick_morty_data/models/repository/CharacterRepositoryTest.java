package com.rick_morty.rick_morty_data.models.repository;

import com.rick_morty.rick_morty_data.model.Character;
import com.rick_morty.rick_morty_data.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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
}
