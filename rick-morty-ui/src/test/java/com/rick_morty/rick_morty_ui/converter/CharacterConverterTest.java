package com.rick_morty.rick_morty_ui.converter;

import com.rick_morty.rick_morty_ui.converters.CharacterConverter;
import com.rick_morty.rick_morty_web_api.api.contract.CharacterDto;
import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterConverterTest {

    @Mock
    private CharacterService characterService;

    @InjectMocks
    private CharacterConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvert() {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setId(1);
        characterDto.setName("Rick Sanchez");

        when(characterService.getCharacterById(1)).thenReturn(characterDto);

        CharacterDto result = converter.convert("1");

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Rick Sanchez", result.getName());

        verify(characterService).getCharacterById(1);
    }

    @Test
    void testConvertEmptyString() {
        CharacterDto result = converter.convert("");
        assertNull(result);
    }
}