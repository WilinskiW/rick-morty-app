package com.rick_morty.rick_morty_web_api.api.controller;

import com.rick_morty.rick_morty_web_api.api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/character")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    /**
     * CREATE
     */

    @PostMapping
    @CachePut(value = "characters", key = "'allCharacter'")
    public ResponseEntity<Void> createCharacter(@RequestBody CharacterSummaryDto characterSummaryDto) {
        characterService.save(characterSummaryDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping("/all")
    @Cacheable(value = "characters", key = "'allCharacter'")
    public ResponseEntity<List<CharacterSummaryDto>> findAll() {
        var result = characterService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterSummaryDto> findById(@PathVariable Integer id) {
        var result = characterService.getCharacterById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("like/{like}")
    public ResponseEntity<List<CharacterSummaryDto>> findByNameLike(@PathVariable String like) {
        var result = characterService.getAllLikeName(like);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/schedule")
    public ResponseEntity<CharacterSummaryDto> findScheduleCharacter() {
        var result = characterService.getScheduleCharacter();
        return ResponseEntity.ok(result);
    }

    /**
     * UPDATE
     */

    @PutMapping("/{id}")
    @CachePut(value = "characters", key = "'allCharacter'")
    public ResponseEntity<Void> updateCharacter(@PathVariable Integer id, @RequestBody CharacterSummaryDto characterSummaryDto) {
        characterService.update(id, characterSummaryDto);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     */

    @DeleteMapping("/{id}")
    @CachePut(value = "characters", key = "'allCharacter'")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Integer id) {
        characterService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
