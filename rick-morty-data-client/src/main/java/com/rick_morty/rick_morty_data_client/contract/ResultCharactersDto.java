package com.rick_morty.rick_morty_data_client.contract;

import java.util.List;

public record ResultCharactersDto(InfoDto info, List<CharacterDto> results) {}
