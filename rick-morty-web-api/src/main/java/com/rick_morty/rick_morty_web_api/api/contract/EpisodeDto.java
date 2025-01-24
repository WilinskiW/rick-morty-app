package com.rick_morty.rick_morty_web_api.api.contract;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDto {
    private int id;
    @NotEmpty(message = "Title can't be empty")
    private String title;
    @NotNull(message = "Air date can't be null")
    @NotEmpty(message = "Air date can't be empty")
    private String airDate;
    @NotEmpty(message = "Episode can't be empty")
    private String episode;
    private List<CharacterDto> characters;
}
