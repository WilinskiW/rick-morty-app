package com.rick_morty.rick_morty_ui.selenium.test.character;

import com.rick_morty.rick_morty_ui.selenium.page.character.CharacterDetailsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
public class CharacterDetailsPageTest {
    private final CharacterDetailsPage characterDetailsPage;
    private final WebDriver driver;

    @AfterEach
    public void tearDown() {
        //driver.quit();
    }

    @BeforeEach
    public void cleanUp() {
        driver.manage().deleteAllCookies();
    }

    public CharacterDetailsPageTest() {
        this.driver = new FirefoxDriver();
        this.characterDetailsPage = new CharacterDetailsPage(driver);
    }

    @Test
    public void openCharacters() {
        characterDetailsPage.open(1);
        characterDetailsPage.openCharacters();

        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void openEpisodes() {
        characterDetailsPage.open(1);
        characterDetailsPage.openEpisodes();

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());

    }

    @Test
    public void openLocations() {
        characterDetailsPage.open(1);
        characterDetailsPage.openLocations();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openLogin() {
        characterDetailsPage.open(1);
        characterDetailsPage.openLogin();

        assertEquals("http://localhost:8082/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void openRegister() {
        characterDetailsPage.open(1);
        characterDetailsPage.openRegister();

        assertEquals("http://localhost:8082/auth/register", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhileAuthenticated() {
        characterDetailsPage.loginAsModerator();
        characterDetailsPage.open(1);
        characterDetailsPage.openAccount();

        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhileNotLogIn() {
        characterDetailsPage.open(1);
        assertThrows(NoSuchElementException.class, () -> characterDetailsPage.openAccount());
    }

    @Test
    public void openHome() {
        characterDetailsPage.open(1);
        characterDetailsPage.openHome();

        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
    }

    @Test
    public void openBack() {
        characterDetailsPage.open(1);
        characterDetailsPage.openBackLink();

        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void openEditLinkWhenAuthorized() {
        characterDetailsPage.loginAsModerator();
        characterDetailsPage.open(1);
        characterDetailsPage.openEditLink();
        assertTrue(driver.getCurrentUrl().contains("character/edit/"));
    }

    @Test
    public void tryEditLinkWhenNotAuthorized() {
        characterDetailsPage.open(1);
        assertThrows(NoSuchElementException.class, () -> characterDetailsPage.openEditLink());
    }

    @Test
    public void tryOpenDeleteLinkWhenNotAuthorized() {
        characterDetailsPage.open(1);
        assertThrows(NoSuchElementException.class, () -> characterDetailsPage.deleteCharacter());
    }

    @Test
    public void openDeleteLink() {
        characterDetailsPage.loginAsAdmin();
        characterDetailsPage.open(5);
        String characterName = characterDetailsPage.deleteCharacter();
        System.out.println("Character name: " + characterName);

        driver.get("http://localhost:8082/character/all");

        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
        assertFalse(driver.getPageSource().contains(characterName));
    }
}
