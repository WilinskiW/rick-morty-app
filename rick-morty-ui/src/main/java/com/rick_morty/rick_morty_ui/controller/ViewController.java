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
}
