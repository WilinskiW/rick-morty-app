package com.rick_morty.rick_morty_ui.selenium.test.character;

import com.rick_morty.rick_morty_ui.selenium.page.character.CharactersPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Profile("test")
public class CharactersPageTest {
    private final CharactersPage charactersPage;
    private final WebDriver driver;

    public CharactersPageTest() {
        this.driver = new FirefoxDriver();
        this.charactersPage = new CharactersPage(driver);
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
        charactersPage.open();
        charactersPage.openHome();
        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
        assertEquals("Rick and Morty - Home Page", driver.getTitle());
    }

    @Test
    public void openCharacterDetailsTest() {
        charactersPage.open();
        charactersPage.openCharacterDetails();

        int mockedId = 5;

        driver.get("http://localhost:8082/character/" + mockedId);

        assertEquals("http://localhost:8082/character/" + mockedId, driver.getCurrentUrl());
    }

    @Test
    public void openEpisodesTest() {
        charactersPage.open();
        charactersPage.openEpisodes();

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
    }

    @Test
    public void openLocationsTest() {
        charactersPage.open();
        charactersPage.openLocations();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openLoginTest() {
        charactersPage.open();
        charactersPage.openLogin();

        assertEquals("http://localhost:8082/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void openRegisterTest() {
        charactersPage.open();
        charactersPage.openRegister();

        assertEquals("http://localhost:8082/auth/register", driver.getCurrentUrl());
    }

    @Test
    public void openFiveMinutesCharacterTest() {
        charactersPage.open();
        charactersPage.openFiveMinutesCharacterDetails();

        assertEquals("http://localhost:8082/character/schedule", driver.getCurrentUrl());
    }

    @Test
    public void openAddNewCharacterWhenAuthorizeTest() {
        charactersPage.loginAsAdmin();
        charactersPage.open();
        charactersPage.openAddNewCharacter();

        assertEquals("http://localhost:8082/character/add", driver.getCurrentUrl());
    }

    @Test
    public void openAddNewCharacterWithNoAuthorizationTest() {
        charactersPage.loginAsModerator();
        charactersPage.open();
        assertThrows(NoSuchElementException.class, () -> charactersPage.openAddNewCharacter());

        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhenItIsAvailableTest() {
        charactersPage.loginAsAdmin();
        charactersPage.open();
        charactersPage.openAccount();

        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openAccountTestWhenItIsNotAvailableTest() {
        charactersPage.open();
        assertThrows(NoSuchElementException.class, () -> charactersPage.openAccount());

        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }
}
