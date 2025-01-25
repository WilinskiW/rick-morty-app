package com.rick_morty.rick_morty_ui.selenium.page.character;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback
public class CharacterDetailsPageTest {
    private final CharacterDetailsPage characterDetailsPage;
    private final WebDriver driver;

    public CharacterDetailsPageTest() {
        this.driver = new FirefoxDriver();
        this.characterDetailsPage = new CharacterDetailsPage(driver);
    }

    @Test
    public void openCharacters() {
        characterDetailsPage.open();
        characterDetailsPage.openCharacters();
    }

    @Test
    public void openEpisodes() {
        characterDetailsPage.open();
        characterDetailsPage.openEpisodes();
    }

    @Test
    public void openLocations() {
        characterDetailsPage.open();
        characterDetailsPage.openLocations();
    }

    @Test
    public void openLogin() {
        characterDetailsPage.open();
        characterDetailsPage.openLogin();
    }

    @Test
    public void openRegister() {
        characterDetailsPage.open();
        characterDetailsPage.openRegister();
    }

    @Test
    public void openAccountWhileAuthenticated() {

        characterDetailsPage.open();

    }

    @Test
    public void openAccountWhileNotLogIn() {
        characterDetailsPage.open();
        assertThrows(NoSuchElementException.class, () -> characterDetailsPage.openAccount());
    }

    @Test
    public void openHome() {
        characterDetailsPage.open();
        characterDetailsPage.openHome();
    }

    @Test
    public void openBack() {
        characterDetailsPage.open();
        characterDetailsPage.openBackLink();
    }

    @Test
    public void openEditLinkWhenAuthorized() {
        characterDetailsPage.loginAsModerator();
        characterDetailsPage.open();
        characterDetailsPage.openEditLink();
    }

    @Test
    public void tryEditLinkWhenNotAuthorized() {
        characterDetailsPage.open();
        assertThrows(NoSuchElementException.class, () -> characterDetailsPage.openEditLink());
    }

    @Test
    public void openDeleteLink() {
        characterDetailsPage.loginAsAdmin();
        characterDetailsPage.open();
        String characterName = characterDetailsPage.deleteCharacter();
        System.out.println("Character name: " + characterName);

        assertFalse(driver.getPageSource().contains(characterName));
        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void tryOpenDeleteLinkWhenNotAuthorized() {
        characterDetailsPage.open();
        assertThrows(NoSuchElementException.class, () -> characterDetailsPage.deleteCharacter());
    }
}
