package com.rick_morty.rick_morty_ui.converter;

import com.rick_morty.rick_morty_ui.converters.LocationSummaryConverter;
import com.rick_morty.rick_morty_web_api.api.contract.LocationDto;
import com.rick_morty.rick_morty_web_api.api.contract.LocationSummaryDto;
import com.rick_morty.rick_morty_web_api.api.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationSummaryConverterTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationSummaryConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvert() {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(1);
        locationDto.setName("Earth");
        locationDto.setType("Planet");
        locationDto.setDimension("C-137");

        when(locationService.getById(1)).thenReturn(locationDto);

        LocationSummaryDto result = converter.convert("1");

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Earth", result.getName());
        assertEquals("Planet", result.getType());
        assertEquals("C-137", result.getDimension());

        verify(locationService).getById(1);
    }

    @Test
    void testConvertEmptyString() {
        LocationSummaryDto result = converter.convert("");
        assertNull(result);
    }
}