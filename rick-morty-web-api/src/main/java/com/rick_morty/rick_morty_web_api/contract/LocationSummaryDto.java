package com.rick_morty.rick_morty_web_api.contract;

public record LocationSummaryDto(int id,
                                 String name,
                                 String type,
                                 String dimension) {
}
