package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_ui.service.AppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
