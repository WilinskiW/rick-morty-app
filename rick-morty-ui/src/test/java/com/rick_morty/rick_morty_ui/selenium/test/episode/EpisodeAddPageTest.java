package com.rick_morty.rick_morty_ui.selenium.test.episode;

import com.rick_morty.rick_morty_ui.selenium.page.episode.EpisodeAddPage;
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
public class EpisodeAddPageTest {
    private final EpisodeAddPage episodeAddPage;
    private final WebDriver driver;

    public EpisodeAddPageTest() {
        this.driver = new FirefoxDriver();
        this.episodeAddPage = new EpisodeAddPage(driver);
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
        episodeAddPage.open();
        episodeAddPage.openEpisodes();

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());

    }

    @Test
    public void openLocations() {
        episodeAddPage.openAsAuthorize();
        episodeAddPage.openLocations();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openLogin() {
        episodeAddPage.openAsAuthorize();
        assertThrows(NoSuchElementException.class, () -> episodeAddPage.openLogin());
    }

    @Test
    public void openRegister() {
        episodeAddPage.openAsAuthorize();
        assertThrows(NoSuchElementException.class, () -> episodeAddPage.openRegister());
    }

    @Test
    public void openAccountWhileAuthenticated() {
        episodeAddPage.openAsAuthorize();
        episodeAddPage.openAccount();

        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openHome() {
        episodeAddPage.openAsAuthorize();
        episodeAddPage.openHome();

        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
    }

    @Test
    public void openAddCharacterPageWithNoAuthorizationTest() {
        episodeAddPage.loginAsModerator();
        episodeAddPage.open();

        driver.get("http://localhost:8082/episode/add");

        assertTrue(driver.getPageSource().contains("Forbidden"));
    }


    @Test
    public void fillOutTheForm() {
        episodeAddPage.openAsAuthorize();
        episodeAddPage.fillOutTheForm("UNIT TEST - add episode");

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains("UNIT TEST"));
    }


    @Test
    public void goBackTest() {
        episodeAddPage.openAsAuthorize();
        episodeAddPage.openBackLink();
        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
    }

    @Test
    public void tryToFillOutCharacterFormWithBlankNameTest() {
        episodeAddPage.loginAsAdmin();
        episodeAddPage.open();
        episodeAddPage.fillOutTheForm("");

        String validationMessage = episodeAddPage.getTitleInput().getAttribute("validationMessage");
        assertNotNull(validationMessage, "Please fill out the field.");
    }

    @Test
    public void tryToSaveWithoutFillingTheFormTest() {
        episodeAddPage.loginAsAdmin();
        episodeAddPage.open();
        episodeAddPage.openSaveButton();

        String validationMessage = episodeAddPage.getTitleInput().getAttribute("validationMessage");
        assertNotNull(validationMessage, "Please fill out the field.");
    }

}
