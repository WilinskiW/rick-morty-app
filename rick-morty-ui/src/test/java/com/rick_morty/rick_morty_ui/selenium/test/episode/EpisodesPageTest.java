package com.rick_morty.rick_morty_ui.selenium.test.episode;

import com.rick_morty.rick_morty_ui.selenium.page.episode.EpisodesPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.*;

@Profile("test")
public class EpisodesPageTest {
    private final EpisodesPage episodesPage;
    private final WebDriver driver;

    public EpisodesPageTest() {
        this.driver = new FirefoxDriver();
        this.episodesPage = new EpisodesPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @BeforeEach
    public void cleanUp() {
        driver.manage().deleteAllCookies();
    }

    @Test
    public void backToHomeTest() {
        episodesPage.open();
        episodesPage.openHome();
        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
        assertEquals("Rick and Morty - Home Page", driver.getTitle());
    }

    @Test
    public void openEpisodeDetailsTest() {
        episodesPage.open();
        episodesPage.openEpisodeDetails();
        assertTrue(driver.getCurrentUrl().contains("http://localhost:8082/episode/"));
    }

    @Test
    public void openCharactersTest() {
        episodesPage.open();
        episodesPage.openCharacters();
        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void openLocationsTest() {
        episodesPage.open();
        episodesPage.openLocations();
        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openLoginTest() {
        episodesPage.open();
        episodesPage.openLogin();
        assertEquals("http://localhost:8082/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void openRegisterTest() {
        episodesPage.open();
        episodesPage.openRegister();
        assertEquals("http://localhost:8082/auth/register", driver.getCurrentUrl());
    }

    @Test
    public void openAddNewEpisodeWhenAuthorizedTest() {
        episodesPage.loginAsAdmin();
        episodesPage.open();
        episodesPage.openAddNewEpisode();
        assertEquals("http://localhost:8082/episode/add", driver.getCurrentUrl());
    }

    @Test
    public void openAddNewEpisodeWithNoAuthorizationTest() {
        episodesPage.loginAsModerator();
        episodesPage.open();
        assertThrows(NoSuchElementException.class, () -> episodesPage.openAddNewEpisode());
        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhenItIsAvailableTest() {
        episodesPage.loginAsAdmin();
        episodesPage.open();
        episodesPage.openAccount();
        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openAccountTestWhenItIsNotAvailableTest() {
        episodesPage.open();
        assertThrows(NoSuchElementException.class, () -> episodesPage.openAccount());
        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
    }
}

