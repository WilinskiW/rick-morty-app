package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import com.rick_morty.rick_morty_web_api.api.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class ViewLocationController implements ViewEntityController<LocationDto> {
    private final LocationService locationService;
    private final CharacterService characterService;

    @Override
    @GetMapping("/{id}")
    public String showSinglePage(@PathVariable int id, Model model) {
        var location = locationService.getById(id);
        model.addAttribute("location", location);
        return "location/location";
    }

    @Override
    @GetMapping("/all")
    public String showAll(Model model) {
        var locations = locationService.getAll();
        model.addAttribute("locations", locations);
        return "location/locations";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        model.addAttribute("location", locationService.getById(id));
        model.addAttribute("allCharacters", characterService.getAll());
        return "location/edit-location";
    }

    @Override
    @GetMapping("/add")
    public String showAddPage(Model model) {
        model.addAttribute("location", new LocationDto());
        model.addAttribute("allCharacters", characterService.getAll());
        return "location/add-location";
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        locationService.deleteById(id);
        return "redirect:/location/all";
    }

    @PostMapping("/{id}/edit/remove-character/{characterId}")
    public String removeCharacter(@PathVariable int id, @PathVariable int characterId) {
        locationService.removeCharacterFromLocation(id, characterId);
        return "redirect:/location/edit/" + id;
    }

    @Override
    @PostMapping("edit/{id}")
    public String update(@PathVariable int id, LocationDto locationDto) {
        locationService.update(id, locationDto);
        return "redirect:/location/all";
    }

    @Override
    @PostMapping("/add")
    public String create(LocationDto locationDto) {
        locationService.save(locationDto);
        return "redirect:/location/all";
    }
}
