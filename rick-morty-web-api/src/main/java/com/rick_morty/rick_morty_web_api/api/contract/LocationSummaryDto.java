package com.rick_morty.rick_morty_web_api.api.contract;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationSummaryDto {
    private int id;
    @NotEmpty(message = "Title can't be empty")
    private String name;
    @Size(min = 3, max = 50, message = "Type is too short or too long")
    private String type;
    @NotEmpty(message = "Dimension can't be empty")
    private String dimension;
}