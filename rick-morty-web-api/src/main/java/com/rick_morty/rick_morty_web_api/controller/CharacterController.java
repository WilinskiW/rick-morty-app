package com.rick_morty.rick_morty_web_api.controller;

import com.rick_morty.rick_morty_web_api.contract.CharacterSummaryDto;
import com.rick_morty.rick_morty_web_api.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/character")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/all")
    public ResponseEntity<List<CharacterSummaryDto>> findAll() {
        var summaryDtos = characterService.getAll();
        return ResponseEntity.of(Optional.ofNullable(summaryDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterSummaryDto> findById(@PathVariable Integer id) {
        var character = characterService.getCharacterById(id);
        return ResponseEntity.of(Optional.ofNullable(character));
    }
}
