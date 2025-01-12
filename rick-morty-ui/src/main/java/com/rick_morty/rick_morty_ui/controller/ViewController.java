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

    @GetMapping("episode")
    public String showEpisodesPage(Model model){
        model.addAttribute("episode", guiClient.getEpisode("/episode/all"));
        return "episodes";
    }
}
