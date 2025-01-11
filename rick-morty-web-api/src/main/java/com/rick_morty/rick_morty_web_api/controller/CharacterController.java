package com.rick_morty.rick_morty_web_api.controller;

import com.rick_morty.rick_morty_web_api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.service.CharacterService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> createCharacter(@RequestBody CharacterSummaryDto characterSummaryDto) {
        characterService.save(characterSummaryDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping("/all")
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

    /**
     * UPDATE
     */

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCharacter(@PathVariable Integer id, @RequestBody CharacterSummaryDto characterSummaryDto) {
        characterService.update(id, characterSummaryDto);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Integer id) {
        characterService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
