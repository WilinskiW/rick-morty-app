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
        return "location";
    }

    @Override
    @GetMapping("/all")
    public String showAll(Model model) {
        var locations = appClient.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        model.addAttribute("location", appClient.getLocation(id));
        model.addAttribute("allCharacters", appClient.getAllCharacters());
        return "edit-location";
    }

    @Override
    public String showAddPage() {
        return "";
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        appClient.delete("location", id);
        return "redirect:/location/all";
    }

    @Override
    @PostMapping("edit/{id}")
    public String update(@PathVariable int id, LocationDto locationDto) {
        appClient.update("location", id, locationDto);
        return "redirect:/location/all";
    }

    @Override
    public String create() {
        return "";
    }
}
