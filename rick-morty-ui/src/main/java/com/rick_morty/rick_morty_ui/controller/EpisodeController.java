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
        return "episode/episode";
    }

    @Override
    @GetMapping("/all")
    public String showAll(Model model) {
        var episodes = appClient.getAllEpisodes();
        model.addAttribute("episodes", episodes);
        return "episode/episodes";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        var episode = appClient.getEpisode(id);
        model.addAttribute("episode", episode);
        model.addAttribute("characters", episode.getCharacters());
        model.addAttribute("allCharacters", appClient.getAllCharacters());
        return "episode/edit-episode";
    }

    @Override
    @GetMapping("/add")
    public String showAddPage(Model model) {
        model.addAttribute("episode", new EpisodeDto());
        model.addAttribute("allCharacters", appClient.getAllCharacters());
        return "episode/add-episode";
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        appClient.delete("episode", id);
        return "redirect:/episode/all";
    }

    @DeleteMapping("/{id}/edit/remove-character/{characterId}")
    public String removeCharacter(@PathVariable int id, @PathVariable int characterId) {
        appClient.removeCharacterFromEntity("episode", id, characterId);
        return "redirect:/episode/edit/" + id;
    }

    @Override
    @PostMapping("/edit/{id}")
    public String update(@PathVariable int id, EpisodeDto episodeDto) {
        appClient.update("episode", id, episodeDto);
        return "redirect:/episode/" + id;
    }

    @Override
    @PostMapping("/add")
    public String create(EpisodeDto episodeDto) {
        appClient.create("episode", episodeDto);
        return "redirect:/episode/all";
    }
}
