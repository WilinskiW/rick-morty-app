package com.rick_morty.rick_morty_web_api.api.controller;

import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.service.EpisodeService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/episodes")
@RequiredArgsConstructor
public class EpisodeController {
    private final EpisodeService episodeService;
    private static final Logger logger = Logger.getLogger(EpisodeController.class.getName());


    /**
     * CREATE
     */

    @PostMapping
    @CachePut(value = "episodes", key = "'allEpisodes'")
    public ResponseEntity<Void> createCharacter(@Valid @RequestBody EpisodeDto episodeDto,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warning("Validation failed for: " + episodeDto);
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        episodeService.save(episodeDto);
        logger.info("Created episode " + episodeDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping
    @Cacheable(value = "episodes", key = "'allEpisodes'")
    public ResponseEntity<List<EpisodeDto>> findAll() {
        var result = episodeService.getAll();
        logger.info("Found " + result.size() + " episodes");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeDto> findById(@PathVariable Integer id) {
        var result = episodeService.getById(id);
        logger.info("Found " + result);
        return ResponseEntity.ok(result);
    }

    /**
     * UPDATE
     */

    @PutMapping("/{id}")
    @CachePut(value = "episodes", key = "'allEpisodes'")
    public ResponseEntity<Void> updateCharacter(@PathVariable Integer id,
                                                @Valid @RequestBody EpisodeDto episodeDto,
                                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.warning("Validation failed for: " + episodeDto);
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        episodeService.update(id, episodeDto);
        logger.info("Updated episode " + episodeDto);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     */

    @DeleteMapping("/{id}")
    @CachePut(value = "episodes", key = "'allEpisodes'")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Integer id) {
        episodeService.deleteById(id);
        logger.info("Deleted episode " + id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/remove-character/{characterId}")
    public ResponseEntity<Void> removeCharacterFromLocation(@PathVariable Integer id, @PathVariable Integer characterId) {
        episodeService.removeCharacterFromLocation(id, characterId);
        logger.info("Removed character from location " + characterId);
        return ResponseEntity.ok().build();
    }
}
