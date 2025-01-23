package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_web_api.api.contract.EpisodeDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import com.rick_morty.rick_morty_web_api.api.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/episode")
@RequiredArgsConstructor
public class ViewEpisodeControllerView implements ViewEntityController<EpisodeDto> {
    private final EpisodeService episodeService;
    private final CharacterService characterService;

    @Override
    @GetMapping("/{id}")
    public String showSinglePage(@PathVariable int id, Model model) {
        var episode = episodeService.getById(id);
        model.addAttribute("episode", episode);
        model.addAttribute("title", episode.getEpisode());
        model.addAttribute("characters", episode.getCharacters());
        return "episode/episode";
    }

    @Override
    @GetMapping("/all")
    public String showAll(Model model) {
        var episodes = episodeService.getAll();
        model.addAttribute("episodes", episodes);
        return "episode/episodes";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        var episode = episodeService.getById(id);
        model.addAttribute("episode", episode);
        model.addAttribute("characters", episode.getCharacters());
        model.addAttribute("allCharacters", characterService.getAll());
        return "episode/edit-episode";
    }

    @Override
    @GetMapping("/add")
    public String showAddPage(Model model) {
        model.addAttribute("episode", new EpisodeDto());
        model.addAttribute("allCharacters", characterService.getAll());
        return "episode/add-episode";
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        episodeService.deleteById(id);
        return "redirect:/episode/all";
    }

    @PostMapping("/{id}/edit/remove-character/{characterId}")
    public String removeCharacter(@PathVariable int id, @PathVariable int characterId) {
        episodeService.removeCharacterFromLocation(id, characterId);
        return "redirect:/episode/edit/" + id;
    }

    @Override
    @PostMapping("/edit/{id}")
    public String update(@PathVariable int id, EpisodeDto episodeDto) {
        episodeService.update(id, episodeDto);
        return "redirect:/episode/" + id;
    }

    @Override
    @PostMapping("/add")
    public String create(EpisodeDto episodeDto) {
        episodeService.save(episodeDto);
        return "redirect:/episode/all";
    }
}
