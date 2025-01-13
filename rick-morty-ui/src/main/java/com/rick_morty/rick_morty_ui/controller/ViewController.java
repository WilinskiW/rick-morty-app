package com.rick_morty.rick_morty_ui.controller;

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
    public String showHomePage(){
        return "homepage";
    }

    @GetMapping("/episodes")
    public String showEpisodesPage(Model model){
        var episodes = appClient.getAllEpisodes();
        model.addAttribute("episodes", episodes);
        return "episodes";
    }

    @GetMapping("/episode/{id}")
    public String showEpisodePage(Model model, @PathVariable int id){
        var episode = appClient.getEpisode("episode", id);
        model.addAttribute("episode", episode);
        model.addAttribute("title", episode.episode());
        model.addAttribute("characters", episode.characters());
        return "episode";
    }

    @GetMapping("characters")
    public String showCharactersPage(Model model){
        model.addAttribute("characters", appClient.getAllCharacters());
        return "characters";
    }

    @GetMapping("character/{id}")
    public String showCharacterPage(Model model, @PathVariable int id){
        var character = appClient.getCharacter("character", id);
        model.addAttribute("character", appClient.getCharacter("character", id));
        model.addAttribute("title", character.name());
        return "character";
    }

    @GetMapping("locations")
    public String showLocationsPage(Model model){
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
}
