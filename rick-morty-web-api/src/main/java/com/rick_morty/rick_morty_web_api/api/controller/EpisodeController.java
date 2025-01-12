package com.rick_morty.rick_morty_web_api.api.controller;

import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/episode")
@RequiredArgsConstructor
public class EpisodeController {
    private final EpisodeService episodeService;

    /**
     * CREATE
     */

    @PostMapping
    @CachePut(value = "episodes", key = "'allEpisodes'")
    public ResponseEntity<Void> createCharacter(@RequestBody EpisodeDto episodeDto) {
        episodeService.save(episodeDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping("/all")
    @Cacheable(value = "episodes", key = "'allEpisodes'")
    public ResponseEntity<List<EpisodeDto>> findAll() {
        var result = episodeService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeDto> findById(@PathVariable Integer id) {
        var result = episodeService.getById(id);
        return ResponseEntity.ok(result);
    }

    /**
     * UPDATE
     */

    @PutMapping("/{id}")
    @CachePut(value = "episodes", key = "'allEpisodes'")
    public ResponseEntity<Void> updateCharacter(@PathVariable Integer id, @RequestBody EpisodeDto episodeDto) {
        episodeService.update(id, episodeDto);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     */

    @DeleteMapping("/{id}")
    @CachePut(value = "episodes", key = "'allEpisodes'")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Integer id) {
        episodeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
