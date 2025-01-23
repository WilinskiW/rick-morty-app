package com.rick_morty.rick_morty_web_api.api.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationSummaryDto {
    private int id;
    private String name;
    private String type;
    private String dimension;
}