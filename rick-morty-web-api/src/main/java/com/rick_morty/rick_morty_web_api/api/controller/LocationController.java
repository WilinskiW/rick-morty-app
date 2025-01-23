package com.rick_morty.rick_morty_web_api.api.controller;

import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/location")
@Slf4j
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private static final Logger logger = Logger.getLogger(LocationController.class.getName());

    /**
     * CREATE
     */

    @PostMapping
    @CachePut(value = "locations", key = "'allLocations'")
    public ResponseEntity<Void> createLocation(@RequestBody LocationDto locationDto) {
        locationService.save(locationDto);
        logger.info("Location created: " + locationDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping("/all")
    @Cacheable(value = "locations", key = "'allLocations'")
    public ResponseEntity<List<LocationDto>> findAll() {
        var result = locationService.getAll();
        logger.info("Locations founded: " + result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> findById(@PathVariable Integer id) {
        var result = locationService.getById(id);
        logger.info("Location found: " + result);
        return ResponseEntity.ok(result);
    }

    /**
     * UPDATE
     */
    @PutMapping("/{id}")
    @CachePut(value = "locations", key = "'allLocations'")
    public ResponseEntity<Void> updateLocation(@PathVariable Integer id, @RequestBody LocationDto locationDto) {
        locationService.update(id, locationDto);
        logger.info("Location updated: " + locationDto);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     */
    @DeleteMapping("/{id}")
    @CachePut(value = "locations", key = "'allLocations'")
    public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
        locationService.deleteById(id);
        logger.info("Location deleted: " + id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/remove-character/{characterId}")
    public ResponseEntity<Void> removeCharacterFromLocation(@PathVariable Integer id, @PathVariable Integer characterId) {
        locationService.removeCharacterFromLocation(id, characterId);
        logger.info("Character deleted: " + characterId);
        return ResponseEntity.ok().build();
    }
}
