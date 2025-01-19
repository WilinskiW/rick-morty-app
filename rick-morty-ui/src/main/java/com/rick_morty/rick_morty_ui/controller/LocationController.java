package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_ui.dto.LocationDto;
import com.rick_morty.rick_morty_ui.service.AppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController implements EntityController<LocationDto> {
    private final AppClient appClient;

    @Override
    @GetMapping("/{id}")
    public String showSinglePage(@PathVariable int id, Model model) {
        var location = appClient.getLocation(id);
        model.addAttribute("location", location);
        return "location/location";
    }

    @Override
    @GetMapping("/all")
    public String showAll(Model model) {
        var locations = appClient.getAllLocations();
        model.addAttribute("locations", locations);
        return "location/locations";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        model.addAttribute("location", appClient.getLocation(id));
        model.addAttribute("allCharacters", appClient.getAllCharacters());
        return "location/edit-location";
    }

    @Override
    @GetMapping("/add")
    public String showAddPage(Model model) {
        model.addAttribute("location", new LocationDto());
        model.addAttribute("allCharacters", appClient.getAllCharacters());
        return "location/add-location";
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        appClient.delete("location", id);
        return "redirect:/location/all";
    }

    @DeleteMapping("/{id}/edit/remove-character/{characterId}")
    public String removeCharacter(@PathVariable int id, @PathVariable int characterId) {
        appClient.removeCharacterFromEntity("location",id, characterId);
        return "redirect:/location/edit/" + id;
    }

    @Override
    @PostMapping("edit/{id}")
    public String update(@PathVariable int id, LocationDto locationDto) {
        appClient.update("location", id, locationDto);
        return "redirect:/location/all";
    }

    @Override
    @PostMapping("/add")
    public String create(LocationDto locationDto) {
        appClient.create("location", locationDto);
        return "redirect:/location/all";
    }
}
