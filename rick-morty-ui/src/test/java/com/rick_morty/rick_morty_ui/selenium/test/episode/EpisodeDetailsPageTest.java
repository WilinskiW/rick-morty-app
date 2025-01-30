package com.rick_morty.rick_morty_ui.selenium.test.episode;

import com.rick_morty.rick_morty_ui.selenium.page.episode.EpisodeDetailsPage;
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
public class EpisodeDetailsPageTest {
    private final EpisodeDetailsPage episodeDetailsPage;
    private final WebDriver driver;

    public EpisodeDetailsPageTest() {
        this.driver = new FirefoxDriver();
        this.episodeDetailsPage = new EpisodeDetailsPage(driver);
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
    public void openCharacters() {
        episodeDetailsPage.open(1);
        episodeDetailsPage.openCharacters();

        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void openEpisodes() {
        episodeDetailsPage.open(1);
        episodeDetailsPage.openEpisodes();

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());

    }

    @Test
    public void openLocations() {
        episodeDetailsPage.open(1);
        episodeDetailsPage.openLocations();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openLogin() {
        episodeDetailsPage.open(1);
        episodeDetailsPage.openLogin();

        assertEquals("http://localhost:8082/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void openRegister() {
        episodeDetailsPage.open(1);
        episodeDetailsPage.openRegister();

        assertEquals("http://localhost:8082/auth/register", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhileAuthenticated() {
        episodeDetailsPage.loginAsModerator();
        episodeDetailsPage.open(1);
        episodeDetailsPage.openAccount();

        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhileNotLogIn() {
        episodeDetailsPage.open(1);
        assertThrows(NoSuchElementException.class, () -> episodeDetailsPage.openAccount());
    }

    @Test
    public void openHome() {
        episodeDetailsPage.open(1);
        episodeDetailsPage.openHome();

        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
    }

    @Test
    public void openBack() {
        episodeDetailsPage.open(1);
        episodeDetailsPage.openBackLink();

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
    }

    @Test
    public void openEditLinkWhenAuthorized() {
        episodeDetailsPage.loginAsModerator();
        episodeDetailsPage.open(1);
        episodeDetailsPage.openEditLink();
        assertTrue(driver.getCurrentUrl().contains("episode/edit/"));
    }

    @Test
    public void tryEditLinkWhenNotAuthorized() {
        episodeDetailsPage.open(1);
        assertThrows(NoSuchElementException.class, () -> episodeDetailsPage.openEditLink());
    }

    @Test
    public void tryOpenDeleteLinkWhenNotAuthorized() {
        episodeDetailsPage.open(1);
        assertThrows(NoSuchElementException.class, () -> episodeDetailsPage.deleteEpisode());
    }

    @Test
    public void openDeleteLink() {
        episodeDetailsPage.loginAsAdmin();
        episodeDetailsPage.open(9);
        String episodeName = episodeDetailsPage.deleteEpisode();
        System.out.println("Episode name: " + episodeName);

        driver.get("http://localhost:8082/episode/all");

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
        assertFalse(driver.getPageSource().contains(episodeName));
    }
}
