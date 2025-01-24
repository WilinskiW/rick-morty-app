package com.rick_morty.rick_morty_ui.selenium.page.home;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HomePageTest {
    private final HomePage homePage;

    public HomePageTest() {
        this.homePage = new HomePage(new FirefoxDriver());
    }

    @Test
    public void openCharactersPage() {
        homePage.open();
        homePage.openCharacters();
    }

    @Test
    public void openLocationsPage() {
        homePage.open();
        homePage.openLocations();
    }

    @Test
    public void openEpisodesPage() {
        homePage.open();
        homePage.openEpisodes();
    }
}
