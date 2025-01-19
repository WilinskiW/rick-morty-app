package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_ui.dto.CharacterDto;
import com.rick_morty.rick_morty_ui.service.AppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController implements EntityController<CharacterDto> {
    private final AppClient appClient;

    @Override
    @GetMapping("/{id}")
    public String showSinglePage(@PathVariable int id, Model model) {
        var character = appClient.getCharacter(id);
        model.addAttribute("character", character);
        return "character";
    }

    @Override
    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("characters", appClient.getAllCharacters());
        return "characters";
    }

    @Override
    @GetMapping("edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        var character = appClient.getCharacter(id);
        var locationsSummary = appClient.getAllLocations().stream()
                .map(l -> l.toLocationSummaryDto())
                .toList();

        model.addAttribute("character", character);
        model.addAttribute("locations", locationsSummary);

        return "edit-character";
    }

    @Override
    public String showAddPage(Model model) {
        return "";
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        appClient.delete("character", id);
        return "redirect:/character/all";
    }

    @Override
    @PostMapping("edit/{id}")
    public String update(@PathVariable int id, CharacterDto tDto) {
        appClient.update("character", id, tDto);
        return "redirect:/character/all";
    }

    @Override
    public String create(CharacterDto characterDto) {
        return "";
    }
}
