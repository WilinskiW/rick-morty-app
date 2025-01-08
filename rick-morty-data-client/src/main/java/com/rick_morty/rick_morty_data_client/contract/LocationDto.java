package com.rick_morty.rick_morty_data_client.contract;

import java.time.LocalDateTime;
import java.util.List;

public record LocationDto(int id,
                          String name,
                          String type,
                          String dimension,
                          List<String> residents,
                          String url,
                          LocalDateTime created) {
}
