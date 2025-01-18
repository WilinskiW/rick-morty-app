package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_ui.dto.EpisodeDto;
import com.rick_morty.rick_morty_ui.service.AppClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/episode")
@RequiredArgsConstructor
public class EpisodeController implements EntityController<EpisodeDto> {
    private final AppClient appClient;

    @Override
    @GetMapping("/{id}")
    public String showSinglePage(@PathVariable int id, Model model) {
        var episode = appClient.getEpisode(id);
        model.addAttribute("episode", episode);
        model.addAttribute("title", episode.getEpisode());
        model.addAttribute("characters", episode.getCharacters());
        return "episode";
    }

    @Override
    @GetMapping("/all")
    public String showAll(Model model) {
        var episodes = appClient.getAllEpisodes();
        model.addAttribute("episodes", episodes);
        return "episodes";
    }

    @Override
    @GetMapping("edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        var episode = appClient.getEpisode(id);
        model.addAttribute("episode", episode);
        return "edit-episode";
    }

    @Override
    public String showAddPage() {
        return "";
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        appClient.delete("episode", id);
        return "redirect:/episode/all";
    }

    @Override
    @PostMapping("edit/{id}")
    public String update(@PathVariable int id, EpisodeDto episodeDto) {
        appClient.update("episode", id, episodeDto);
        return "redirect:/episode/all";
    }

    @Override
    public String create() {
        return "";
    }
}
