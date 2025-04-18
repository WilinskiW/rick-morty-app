package com.rick_morty.rick_morty_web_api.api.controller;

import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.service.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/locations")
@Slf4j
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private static final Logger logger = Logger.getLogger(LocationController.class.getName());

    /**
     * CREATE
     */

    @PostMapping
    public ResponseEntity<Void> createLocation(@Valid @RequestBody LocationDto locationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warning("Validation failed for: " + locationDto);
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        locationService.save(locationDto);
        logger.info("Location created: " + locationDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping
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
    public ResponseEntity<Void> updateLocation(@PathVariable Integer id,
                                               @Valid @RequestBody LocationDto locationDto,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warning("Validation failed for: " + locationDto);
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        locationService.update(id, locationDto);
        logger.info("Location updated: " + locationDto);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     */
    @DeleteMapping("/{id}")
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
