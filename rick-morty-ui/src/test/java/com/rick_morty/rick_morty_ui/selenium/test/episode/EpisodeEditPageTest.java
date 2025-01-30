package com.rick_morty.rick_morty_ui.selenium.test.episode;

import com.rick_morty.rick_morty_ui.selenium.page.episode.EpisodeEditPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
public class EpisodeEditPageTest {
    private final EpisodeEditPage episodeEditPage;
    private final WebDriver driver;

    public EpisodeEditPageTest() {
        this.driver = new FirefoxDriver();
        this.episodeEditPage = new EpisodeEditPage(driver);
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
    public void openEpisodes() {
        episodeEditPage.open(1);
        episodeEditPage.openEpisodes();

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());

    }

    @Test
    public void openLocations() {
        episodeEditPage.open(1);
        episodeEditPage.openLocations();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openLogin() {
        episodeEditPage.open(1);
        assertThrows(NoSuchElementException.class, () -> episodeEditPage.openLogin());
    }

    @Test
    public void openRegister() {
        episodeEditPage.open(1);
        assertThrows(NoSuchElementException.class, () -> episodeEditPage.openRegister());
    }

    @Test
    public void openAccountWhileAuthenticated() {
        episodeEditPage.loginAsModerator();
        episodeEditPage.open(1);
        episodeEditPage.openAccount();

        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhileNotLogIn() {
        episodeEditPage.open(1);
        assertThrows(NoSuchElementException.class, () -> episodeEditPage.openAccount());
    }

    @Test
    public void openHome() {
        episodeEditPage.open(1);
        episodeEditPage.openHome();

        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
    }

    @Test
    public void editEpisode_addNewOneCharacter_andSubmitTest() {
        int episodeId = 4;
        episodeEditPage.openAsAuthorize(episodeId);
        episodeEditPage.fillTheEditForm("UNIT TEST - New Episode Title", "April 24, 2025", "S??E??");
        String selectedCharacter = episodeEditPage.selectOneCharacter();
        episodeEditPage.submitEpisode();

        assertEquals("http://localhost:8082/episode/" + episodeId, driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains("UNIT TEST"));
        assertTrue(driver.getPageSource().contains(selectedCharacter));
    }

    @Test
    public void editEpisode_addFewNewCharacters_andSubmitTest() {
        int episodeId = 7;
        episodeEditPage.openAsAuthorize(episodeId);
        episodeEditPage.fillTheEditForm("UNIT TEST - New Episode Title", "April 24, 2025", "S??E??");
        List<String> selectedCharacters = episodeEditPage.selectManyCharacters();
        episodeEditPage.submitEpisode();

        assertEquals("http://localhost:8082/episode/" + episodeId, driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains("UNIT TEST"));
        for (String character : selectedCharacters) {
            assertTrue(driver.getPageSource().contains(character));
        }
    }

    @Test
    public void tryToSubmitFormWhileAtLeastOneFieldIsEmptyTest() {
        int episodeId = 4;
        episodeEditPage.openAsAuthorize(episodeId);
        episodeEditPage.fillTheEditForm("", "", "");
        episodeEditPage.submitEpisode();

        assertEquals("http://localhost:8082/episode/edit/" + episodeId, driver.getCurrentUrl());
        String validationMessage = episodeEditPage.getTitleInput().getAttribute("validationMessage");
        assertNotNull(validationMessage, "Please fill out the field.");
    }

    @Test
    public void openCharacterEditPage() {
        episodeEditPage.openAsAuthorize(5);
        episodeEditPage.openCharacterEdit();
        assertTrue(driver.getCurrentUrl().contains("http://localhost:8082/character/edit/"));
    }

    @Test
    public void deleteCharacterFromEpisodeTest() {
        int episodeId = 5;
        episodeEditPage.openAsAuthorize(episodeId);
        String deleted = episodeEditPage.deleteCharacter();
        assertEquals("http://localhost:8082/episode/edit/" + episodeId, driver.getCurrentUrl());
        boolean isDeletedInSelect = episodeEditPage.getCharacterSelect().getOptions().stream()
                .anyMatch(option -> option.getText().equals(deleted));
        assertTrue(isDeletedInSelect);
    }

    @Test
    public void backToEpisodesPageTest() {
        episodeEditPage.openAsAuthorize(1);
        episodeEditPage.backToEpisodes();
    }

}
