package com.rick_morty.rick_morty_ui.dto;

import lombok.Data;

import java.util.List;

@Data
public class EpisodeDto{
    private int id;
    private String title;
    private String airDate;
    private String episode;
    private List<CharacterDto> characters;
}
