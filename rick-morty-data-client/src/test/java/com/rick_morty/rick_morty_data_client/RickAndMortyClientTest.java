package com.rick_morty.rick_morty_data_client;

import com.rick_morty.rick_morty_data_client.contract.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RickAndMortyClientTest {
    @Mock
    private RickAndMortyUriBuilderProvider provider;

    @InjectMocks
    private RickAndMortyClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(provider.builder()).thenAnswer(a -> UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("rickandmortyapi.com")
                .pathSegment("api"));
    }

    // Character tests

    @Test
    void getCharactersTest() {
        List<CharacterDto> characters = client.getCharacters();

        assertNotNull(characters);
        assertEquals(826, characters.size());
        assertEquals("Rick Sanchez", characters.getFirst().name());
        assertEquals("Butter Robot", characters.getLast().name());
    }

    @Test
    void getCharacterByIdTest(){
        CharacterDto characterDto = client.getCharacter(50);
        CharacterDto expectedCharacterDto = new CharacterDto(
                50,
                "Blim Blam",
                "Alive",
                "Alien",
                "Korblock",
                "Male",
                new CharacterDto.LocationDto("unknown", ""), // Origin
                new CharacterDto.LocationDto("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"), // Location
                "https://rickandmortyapi.com/api/character/avatar/50.jpeg",
                List.of("https://rickandmortyapi.com/api/episode/14"), // Episodes
                "https://rickandmortyapi.com/api/character/50",
                LocalDateTime.parse("2017-11-05T11:21:43.756Z".replace("Z",""))
        );
        assertEquals(expectedCharacterDto, characterDto);
    }

    @Test
    void getCharacterPageTest(){
        ResultCharactersDto resultCharactersDto = client.getCharactersPage(1);
        ResultCharactersDto expectedCharacterDto = new ResultCharactersDto(
                new InfoDto(826, 42, "https://rickandmortyapi.com/api/character?page=2", null),
                null);

        assertNotNull(resultCharactersDto);
        assertEquals(expectedCharacterDto.info(), resultCharactersDto.info());
        assertEquals(20, resultCharactersDto.results().size());
    }

    //Location tests

    @Test
    void getLocationsTest() {
        List<LocationDto> locations = client.getLocations();

        assertNotNull(locations);
        assertEquals(126, locations.size());
        assertEquals("Earth (C-137)", locations.getFirst().name());
        assertEquals("Rick's Memories", locations.getLast().name());
    }

    @Test
    void getLocationById(){
        LocationDto locationDto = client.getLocation(26);
        LocationDto expectedLocationDto = new LocationDto(
                26,
                "Earth (K-83)",
                "Planet",
                "Dimension K-83",
                new ArrayList<>(),
                "https://rickandmortyapi.com/api/location/26",
                LocalDateTime.parse("2017-11-30T11:41:11.452")

        );
        assertEquals(expectedLocationDto, locationDto);
    }


    @Test
    void getLocationPage(){
        ResultLocationDto resultLocationDto = client.getLocationsPage(4);
        ResultLocationDto expectedLocationDto = new ResultLocationDto(
                new InfoDto(126, 7, "https://rickandmortyapi.com/api/location?page=5",
                        "https://rickandmortyapi.com/api/location?page=3"),
                null);

        assertNotNull(resultLocationDto);
        assertEquals(expectedLocationDto.info(), resultLocationDto.info());
        assertEquals(20, resultLocationDto.results().size());
    }

    // Episode test

    @Test
    void getEpisodesTest(){
        List<EpisodeDto> episodeDtos = client.getEpisodes();

        assertNotNull(episodeDtos);
        assertEquals(51, episodeDtos.size());
        assertEquals("Pilot", episodeDtos.getFirst().name());
        assertEquals("Rickmurai Jack", episodeDtos.getLast().name());
    }

    @Test
    void getEpisodeByIdTest(){
        EpisodeDto episodeDto = client.getEpisode(24);

        List<String> urls = List.of(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2",
                "https://rickandmortyapi.com/api/character/3",
                "https://rickandmortyapi.com/api/character/4",
                "https://rickandmortyapi.com/api/character/9",
                "https://rickandmortyapi.com/api/character/70",
                "https://rickandmortyapi.com/api/character/107",
                "https://rickandmortyapi.com/api/character/167",
                "https://rickandmortyapi.com/api/character/171",
                "https://rickandmortyapi.com/api/character/189",
                "https://rickandmortyapi.com/api/character/240",
                "https://rickandmortyapi.com/api/character/265",
                "https://rickandmortyapi.com/api/character/272",
                "https://rickandmortyapi.com/api/character/276",
                "https://rickandmortyapi.com/api/character/329"
        );

        EpisodeDto expectedEpisodeDto = new EpisodeDto(
                24,
                "Pickle Rick",
                "August 6, 2017",
                "S03E03",
                urls,
                "https://rickandmortyapi.com/api/episode/24",
                LocalDateTime.parse("2017-11-10T12:56:36.206"));

        assertNotNull(episodeDto);
        assertEquals(expectedEpisodeDto, episodeDto);
    }

    @Test
    void getEpisodePageTest(){
        ResultEpisodesDto resultEpisodesDto = client.getEpisodesPage(2);
        ResultEpisodesDto expectedResultEpisodeDto = new ResultEpisodesDto(
                new InfoDto(51, 3, "https://rickandmortyapi.com/api/episode?page=3",
                        "https://rickandmortyapi.com/api/episode?page=1"),
                null);

        assertNotNull(resultEpisodesDto);
        assertEquals(expectedResultEpisodeDto.info(), resultEpisodesDto.info());
        assertEquals(20, resultEpisodesDto.results().size());
    }
}