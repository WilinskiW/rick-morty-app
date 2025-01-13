package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_ui.service.GuiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final GuiClient guiClient;

    @GetMapping("/")
    public String showHomePage(){
        return "homepage";
    }

    @GetMapping("/episode")
    public String showEpisodePage(Model model){
        var episode = guiClient.getEpisode("/episode/5");
        model.addAttribute("episode", episode);
        model.addAttribute("title", episode.episode());
        model.addAttribute("characters", episode.characters());
        return "episode";
    }

    @GetMapping("characters")
    public String showCharactersPage(Model model){
        model.addAttribute("characters", guiClient.getAllCharacters());
        return "characters";
    }
}
