package com.rick_morty.rick_morty_web_api.api.controller;

import com.rick_morty.rick_morty_web_api.api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createCharacter(@RequestBody CharacterSummaryDto characterSummaryDto) {
        characterService.save(characterSummaryDto);
        logger.info("Character created: " + characterSummaryDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping("/all")
    @Cacheable(value = "characters", key = "'allCharacter'")
    public ResponseEntity<List<CharacterSummaryDto>> findAll() {
        var result = characterService.getAll();
        logger.info("Characters founded: " + result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterSummaryDto> findById(@PathVariable Integer id) {
        var result = characterService.getCharacterById(id);
        logger.info("Character found: " + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("like/{like}")
    public ResponseEntity<List<CharacterSummaryDto>> findByNameLike(@PathVariable String like) {
        var result = characterService.getAllLikeName(like);
        logger.info("Characters found like " + like + " founded: "  + result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/schedule")
    public ResponseEntity<CharacterSummaryDto> findScheduleCharacter() {
        var result = characterService.getScheduleCharacter();
        logger.info("Characters found schedule: " + result);
        return ResponseEntity.ok(result);
    }

    /**
     * UPDATE
     */

    @PutMapping("/{id}")
    @CachePut(value = "characters", key = "'allCharacter'")
    public ResponseEntity<Void> updateCharacter(@PathVariable Integer id, @RequestBody CharacterSummaryDto characterSummaryDto) {
        characterService.update(id, characterSummaryDto);
        logger.info("Character updated: " + characterSummaryDto);
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
