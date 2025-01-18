package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_ui.dto.CharacterDto;
import com.rick_morty.rick_morty_ui.service.AppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final AppClient appClient;

    @GetMapping("/")
    public String showHomePage() {
        return "homepage";
    }

    @GetMapping("/episodes")
    public String showEpisodesPage(Model model) {
        var episodes = appClient.getAllEpisodes();
        model.addAttribute("episodes", episodes);
        return "episodes";
    }

    @GetMapping("/episode/{id}")
    public String showEpisodePage(Model model, @PathVariable int id) {
        var episode = appClient.getEpisode("episode", id);
        model.addAttribute("episode", episode);
        model.addAttribute("title", episode.getEpisode());
        model.addAttribute("characters", episode.getCharacters());
        return "episode";
    }

    @GetMapping("characters")
    public String showCharactersPage(Model model) {
        model.addAttribute("characters", appClient.getAllCharacters());
        return "characters";
    }

    @GetMapping("character/{id}")
    public String showCharacterPage(Model model, @PathVariable int id) {
        var character = appClient.getCharacter("character", id);
        model.addAttribute("character", character);
        return "character";
    }

    @GetMapping("locations")
    public String showLocationsPage(Model model) {
        var locations = appClient.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }


    @GetMapping("location/{id}")
    public String showLocationPage(Model model, @PathVariable int id) {
        var location = appClient.getLocation("location", id);
        model.addAttribute("location", location);
        return "location";
    }

    @GetMapping("edit/episode/{id}")
    public String showEpisodeEditPage(@PathVariable int id, Model model) {
        var episode = appClient.getEpisode("episode", id);
        model.addAttribute("episode", episode);
        return "edit-episode";
    }

    @GetMapping("edit/character/{id}")
    public String showCharacterEditPage(@PathVariable int id, Model model) {
        var character = appClient.getCharacter("character", id);
        var locationsSummary = appClient.getAllLocations().stream()
                .map(l -> l.toLocationSummaryDto())
                .toList();

        model.addAttribute("character", character);
        model.addAttribute("locations", locationsSummary);

        return "edit-character";
    }

    @GetMapping("edit/location/{id}")
    public String showLocationEditPage(@PathVariable int id, Model model) {
        var location = appClient.getLocation("location", id);
        model.addAttribute("location", location);
        return "edit-character";
    }
}
