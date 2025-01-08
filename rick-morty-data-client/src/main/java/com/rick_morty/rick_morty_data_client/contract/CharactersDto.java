package com.rick_morty.rick_morty_data_client.contract;

import java.util.List;

public record CharactersDto(InfoDto info, List<CharacterDto> results) {}
