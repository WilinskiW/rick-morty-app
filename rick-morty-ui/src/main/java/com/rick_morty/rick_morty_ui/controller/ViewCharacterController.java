package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import com.rick_morty.rick_morty_web_api.api.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/character")
@RequiredArgsConstructor
public class ViewCharacterController implements ViewEntityController<CharacterDto> {
    private final CharacterService characterService;
    private final LocationService locationService;

    @Override
    @GetMapping("/{id}")
    public String showSinglePage(@PathVariable int id, Model model) {
        var character = characterService.getCharacterById(id);
        model.addAttribute("character", character);
        return "character/character";
    }

    @GetMapping("/schedule")
    public String showScheduleCharacterPage(Model model) {
        var character = characterService.getScheduleCharacter();
        model.addAttribute("character", character);
        return "character/character";
    }

    @Override
    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("characters", characterService.getAll());
        return "character/characters";
    }

    @Override
    @GetMapping("edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        var character = characterService.getCharacterById(id);

        model.addAttribute("character", character);
        model.addAttribute("locations", locationService.getAll());

        return "character/edit-character";
    }


    @Override
    @GetMapping("/add")
    public String showAddPage(Model model) {
        model.addAttribute("character", new CharacterDto());
        model.addAttribute("locations", locationService.getAll());
        return "character/add-character";
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        characterService.deleteById(id);
        return "redirect:/character/all";
    }

    @Override
    @PostMapping("edit/{id}")
    public String update(@PathVariable int id, CharacterDto tDto) {
        characterService.update(id, tDto);
        return "redirect:/character/all";
    }

    @Override
    @PostMapping("/add")
    public String create(CharacterDto characterDto) {
        characterService.save(characterDto);
        return "redirect:/character/all";
    }
}
