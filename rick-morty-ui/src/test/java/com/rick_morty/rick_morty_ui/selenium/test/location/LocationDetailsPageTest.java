package com.rick_morty.rick_morty_ui.selenium.test.location;

import com.rick_morty.rick_morty_ui.selenium.page.location.LocationDetailsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:reset_data.sql")
public class LocationDetailsPageTest {
    private final LocationDetailsPage locationDetailsPage;
    private final WebDriver driver;

    public LocationDetailsPageTest() {
        this.driver = new FirefoxDriver();
        this.locationDetailsPage = new LocationDetailsPage(driver);
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
        locationDetailsPage.open(1);
        locationDetailsPage.openCharacters();

        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void openEpisodes() {
        locationDetailsPage.open(1);
        locationDetailsPage.openEpisodes();

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());

    }

    @Test
    public void openLocations() {
        locationDetailsPage.open(1);
        locationDetailsPage.openLocations();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openLogin() {
        locationDetailsPage.open(1);
        locationDetailsPage.openLogin();

        assertEquals("http://localhost:8082/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void openRegister() {
        locationDetailsPage.open(1);
        locationDetailsPage.openRegister();

        assertEquals("http://localhost:8082/auth/register", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhileAuthenticated() {
        locationDetailsPage.loginAsModerator();
        locationDetailsPage.open(1);
        locationDetailsPage.openAccount();

        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhileNotLogIn() {
        locationDetailsPage.open(1);
        assertThrows(NoSuchElementException.class, () -> locationDetailsPage.openAccount());
    }

    @Test
    public void openHome() {
        locationDetailsPage.open(1);
        locationDetailsPage.openHome();

        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
    }

    @Test
    public void openBack() {
        locationDetailsPage.open(1);
        locationDetailsPage.openBackLink();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openEditLinkWhenAuthorized() {
        locationDetailsPage.loginAsModerator();
        locationDetailsPage.open(1);
        locationDetailsPage.openEditLink();
        assertTrue(driver.getCurrentUrl().contains("location/edit/"));
    }

    @Test
    public void tryEditLinkWhenNotAuthorized() {
        locationDetailsPage.open(1);
        assertThrows(NoSuchElementException.class, () -> locationDetailsPage.openEditLink());
    }

    @Test
    public void tryOpenDeleteLinkWhenNotAuthorized() {
        locationDetailsPage.open(1);
        assertThrows(NoSuchElementException.class, () -> locationDetailsPage.deleteLocation());
    }

    @Test
    public void openDeleteLink() {
        locationDetailsPage.loginAsAdmin();
        locationDetailsPage.open(9);
        String locationName = locationDetailsPage.deleteLocation();

        driver.get("http://localhost:8082/location/all");

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
        assertFalse(driver.getPageSource().contains(locationName));
    }
}
