package com.rick_morty.rick_morty_web_api.api.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDto {
    private int id;
    private String title;
    private String airDate;
    private String episode;
    private List<CharacterDto> characters;
}
