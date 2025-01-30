package com.rick_morty.rick_morty_ui.selenium.test.location;

import com.rick_morty.rick_morty_ui.selenium.page.location.LocationsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Profile("test")
public class LocationsPageTest {
    private final LocationsPage locationsPage;
    private final WebDriver driver;

    public LocationsPageTest() {
        this.driver = new FirefoxDriver();
        this.locationsPage = new LocationsPage(driver);
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
        locationsPage.open();
        locationsPage.openHome();
        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
        assertEquals("Rick and Morty - Home Page", driver.getTitle());
    }

    @Test
    public void openLocationDetailsTest() {
        locationsPage.open();
        locationsPage.openLocationDetails();
        assertTrue(driver.getCurrentUrl().contains("http://localhost:8082/location/"));
    }

    @Test
    public void openCharactersTest() {
        locationsPage.open();
        locationsPage.openCharacters();
        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void openEpisodesTest() {
        locationsPage.open();
        locationsPage.openEpisodes();
        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
    }

    @Test
    public void openLoginTest() {
        locationsPage.open();
        locationsPage.openLogin();
        assertEquals("http://localhost:8082/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void openRegisterTest() {
        locationsPage.open();
        locationsPage.openRegister();
        assertEquals("http://localhost:8082/auth/register", driver.getCurrentUrl());
    }

    @Test
    public void openAddNewLocationWhenAuthorizedTest() {
        locationsPage.loginAsAdmin();
        locationsPage.open();
        locationsPage.openAddNewLocation();
        assertEquals("http://localhost:8082/location/add", driver.getCurrentUrl());
    }

    @Test
    public void openAddNewLocationWithNoAuthorizationTest() {
        locationsPage.loginAsModerator();
        locationsPage.open();
        assertThrows(NoSuchElementException.class, () -> locationsPage.openAddNewLocation());
        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhenItIsAvailableTest() {
        locationsPage.loginAsAdmin();
        locationsPage.open();
        locationsPage.openAccount();
        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openAccountTestWhenItIsNotAvailableTest() {
        locationsPage.open();
        assertThrows(NoSuchElementException.class, () -> locationsPage.openAccount());
        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }
}
