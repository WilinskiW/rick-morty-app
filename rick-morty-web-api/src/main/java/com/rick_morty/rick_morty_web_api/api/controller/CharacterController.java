package com.rick_morty.rick_morty_web_api.api.controller;

import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/character")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;
    private static final Logger logger = Logger.getLogger(CharacterController.class.getName());

    /**
     * CREATE
     */

    @PostMapping
    @CachePut(value = "characters", key = "'allCharacter'")
    public ResponseEntity<Void> createCharacter(@Valid @RequestBody CharacterDto characterDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            characterService.save(characterDto);
            logger.info("Character created: " + characterDto);
        }
        else {
            throw new IllegalArgumentException("");
        }
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping("/all")
    @Cacheable(value = "characters", key = "'allCharacter'")
    public ResponseEntity<List<CharacterDto>> findAll() {
        var result = characterService.getAll();
        logger.info("Characters founded: " + result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> findById(@PathVariable Integer id) {
        var result = characterService.getCharacterById(id);
        logger.info("Character found: " + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("like/{like}")
    public ResponseEntity<List<CharacterDto>> findByNameLike(@PathVariable String like) {
        var result = characterService.getAllLikeName(like);
        logger.info("Characters found like " + like + " founded: "  + result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/schedule")
    public ResponseEntity<CharacterDto> findScheduleCharacter() {
        var result = characterService.getScheduleCharacter();
        logger.info("Characters found schedule: " + result);
        return ResponseEntity.ok(result);
    }

    /**
     * UPDATE
     */

    @PutMapping("/{id}")
    @CachePut(value = "characters", key = "'allCharacter'")
    public ResponseEntity<Void> updateCharacter(@PathVariable Integer id, @Valid @RequestBody CharacterDto characterDto) {
        characterService.update(id, characterDto);
        logger.info("Character updated: " + characterDto);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     */

    @DeleteMapping("/{id}")
    @CachePut(value = "characters", key = "'allCharacter'")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Integer id) {
        characterService.deleteById(id);
        logger.info("Character deleted: " + id);
        return ResponseEntity.ok().build();
    }
}
