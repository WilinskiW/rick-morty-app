package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_ui.dto.CharacterDto;
import com.rick_morty.rick_morty_ui.dto.EpisodeDto;
import com.rick_morty.rick_morty_ui.dto.LocationDto;
import com.rick_morty.rick_morty_ui.service.AppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ModificationController {
    private final AppClient appClient;

    @DeleteMapping("character/{id}")
    public String deleteCharacter(@PathVariable("id") int id) {
        appClient.delete("character", id);
        return "redirect:/characters";
    }

    @DeleteMapping("location/{id}")
    public String deleteLocation(@PathVariable("id") int id) {
        appClient.delete("location", id);
        return "redirect:/locations";
    }

    @DeleteMapping("episode/{id}")
    public String deleteEpisodes(@PathVariable("id") int id) {
        appClient.delete("episode", id);
        return "redirect:/episodes";
    }

    @PostMapping("edit/episode/{id}")
    public String editEpisode(@PathVariable("id") int id, @ModelAttribute EpisodeDto episode) {
        appClient.update("episode", id, episode);
        return "redirect:/episodes";
    }

    @PostMapping("edit/character/{id}")
    public String editCharacter(@PathVariable("id") int id, @ModelAttribute CharacterDto character){
        appClient.update("character", id, character);

        return "redirect:/characters";
    }

    @PostMapping("edit/location/{id}")
    public String editLocation(@PathVariable("id") int id, @ModelAttribute LocationDto locationDto){
        appClient.update("location", id, locationDto);

        return "redirect:/locations";
    }}
