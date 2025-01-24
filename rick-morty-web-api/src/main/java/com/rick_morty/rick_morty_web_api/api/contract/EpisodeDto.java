package com.rick_morty.rick_morty_web_api.api.contract;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
    @Past(message = "Air date must be in the past")
    private String airDate;
    @NotEmpty
    private String episode;
    private List<CharacterDto> characters;
}
