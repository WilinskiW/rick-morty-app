package com.rick_morty.rick_morty_ui.selenium.page.home;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePageTest {
    private final HomePage homePage;
    private final WebDriver driver;

    public HomePageTest() {
        this.driver = new FirefoxDriver();
        this.homePage = new HomePage(driver);
    }

    @Test
    public void openCharactersPage() {
        homePage.open();
        homePage.openCharacters();
        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void openLocationsPage() {
        homePage.open();
        homePage.openLocations();
        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openEpisodesPage() {
        homePage.open();
        homePage.openEpisodes();
        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
    }
}
