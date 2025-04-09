package com.rick_morty.rick_morty_web_api.api.controller;

import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;
    private static final Logger logger = Logger.getLogger(CharacterController.class.getName());

    /**
     * CREATE
     */

    @PostMapping
    public ResponseEntity<Void> createCharacter(@Valid @RequestBody CharacterDto characterDto,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warning("Validation failed for: " + characterDto);
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        characterService.save(characterDto);
        logger.info("Character created: " + characterDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping
    public ResponseEntity<Page<CharacterDto>> findAll(@RequestParam Integer page) {
        var result = characterService.getAll(page);
        logger.info("Characters founded: " + result.getTotalElements());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> findById(@PathVariable Integer id) {
        var result = characterService.getCharacterById(id);
        logger.info("Character found: " + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{locationId}/notInLocation")
    public ResponseEntity<List<CharacterDto>> findCharactersNotInLocation(@PathVariable Integer locationId) {
        var result = characterService.getAllNotInTheLocation(locationId);
        logger.info("Characters not in location " + locationId + " : " + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{episodeId}/notInEpisode")
    public ResponseEntity<List<CharacterDto>> findCharactersNotInEpisode(@PathVariable Integer episodeId) {
        var result = characterService.getAllNotInTheLocation(episodeId);
        logger.info("Characters not in episode " + episodeId + " : " + result);
        return ResponseEntity.ok(result);
    }

    /**
     * UPDATE
     */

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCharacter(@PathVariable Integer id,
                                                @Valid @RequestBody CharacterDto characterDto,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warning("Validation failed for: " + characterDto);
            throw new ValidationException();
        }

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
