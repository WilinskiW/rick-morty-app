package com.rick_morty.rick_morty_web_api.controller;

import com.rick_morty.rick_morty_web_api.contract.CreateLocationDto;
import com.rick_morty.rick_morty_web_api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.contract.LocationSummaryDto;
import com.rick_morty.rick_morty_web_api.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    /**
     * CREATE
     */

    @PostMapping
    public ResponseEntity<Void> createLocation(@RequestBody CreateLocationDto createLocationDto) {
        locationService.save(createLocationDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping("/all")
    public ResponseEntity<List<LocationDto>> findAll() {
        var result = locationService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> findById(@PathVariable Integer id) {
        var result = locationService.getById(id);
        return ResponseEntity.ok(result);
    }

    /**
     * UPDATE
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLocation(@PathVariable Integer id, @RequestBody LocationSummaryDto locationSummaryDto) {
        locationService.update(id, locationSummaryDto);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
        locationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
