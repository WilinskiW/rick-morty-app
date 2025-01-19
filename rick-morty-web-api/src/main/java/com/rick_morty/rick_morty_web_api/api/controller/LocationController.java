package com.rick_morty.rick_morty_web_api.api.controller;

import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import com.rick_morty.rick_morty_web_api.api.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @CachePut(value = "locations", key = "'allLocations'")
    public ResponseEntity<Void> createLocation(@RequestBody LocationDto locationDto) {
        locationService.save(locationDto);
        return ResponseEntity.ok().build();
    }

    /**
     * READ
     */

    @GetMapping("/all")
    @Cacheable(value = "locations", key = "'allLocations'")
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
    @CachePut(value = "locations", key = "'allLocations'")
    public ResponseEntity<Void> updateLocation(@PathVariable Integer id, @RequestBody LocationSummaryDto locationSummaryDto) {
        locationService.update(id, locationSummaryDto);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     */
    @DeleteMapping("/{id}")
    @CachePut(value = "locations", key = "'allLocations'")
    public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
        locationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
