package com.rick_morty.rick_morty_ui.selenium.test.location;

import com.rick_morty.rick_morty_ui.selenium.page.location.LocationEditPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:reset_data.sql")
public class LocationEditPageTest {
    private final LocationEditPage locationEditPage;
    private final WebDriver driver;

    public LocationEditPageTest() {
        this.driver = new FirefoxDriver();
        this.locationEditPage = new LocationEditPage(driver);
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
        locationEditPage.open(1);
        locationEditPage.openEpisodes();

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());
    }

    @Test
    public void openLocations() {
        locationEditPage.open(1);
        locationEditPage.openLocations();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openLogin() {
        locationEditPage.open(1);
        assertThrows(NoSuchElementException.class, () -> locationEditPage.openLogin());
    }

    @Test
    public void openRegister() {
        locationEditPage.open(1);
        assertThrows(NoSuchElementException.class, () -> locationEditPage.openRegister());
    }

    @Test
    public void openAccountWhileAuthenticated() {
        locationEditPage.loginAsModerator();
        locationEditPage.open(1);
        locationEditPage.openAccount();

        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openAccountWhileNotLogIn() {
        locationEditPage.open(1);
        assertThrows(NoSuchElementException.class, () -> locationEditPage.openAccount());
    }

    @Test
    public void openHome() {
        locationEditPage.open(1);
        locationEditPage.openHome();

        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
    }

    @Test
    public void editLocation_addNewOneCharacter_andSubmitTest() {
        int locationId = 4;
        locationEditPage.openAsAuthorize(locationId);
        locationEditPage.fillTheEditForm("UNIT TEST - new location name", "Planet", "Test dimension");
        String selectedCharacter = locationEditPage.selectOneCharacter();
        locationEditPage.submitLocation();

        assertEquals("http://localhost:8082/location/" + locationId, driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains("UNIT TEST"));
        assertTrue(driver.getPageSource().contains(selectedCharacter));
    }

    @Test
    public void editLocation_addFewNewCharacters_andSubmitTest() {
        int locationId = 7;
        locationEditPage.openAsAuthorize(locationId);
        locationEditPage.fillTheEditForm("UNIT TEST 2 - new location name", "Planet", "Test dimension");
        List<String> selectedCharacters = locationEditPage.selectManyCharacters();
        locationEditPage.submitLocation();

        assertEquals("http://localhost:8082/location/" + locationId, driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains("UNIT TEST"));
        for (String character : selectedCharacters) {
            assertTrue(driver.getPageSource().contains(character));
        }
    }

    @Test
    public void tryToSubmitFormWhileAtLeastOneFieldIsEmptyTest() {
        int locationId = 4;
        locationEditPage.openAsAuthorize(locationId);
        locationEditPage.fillTheEditForm("", "", "");
        locationEditPage.submitLocation();

        assertEquals("http://localhost:8082/location/edit/" + locationId, driver.getCurrentUrl());
        String validationMessage = locationEditPage.getNameInput().getAttribute("validationMessage");
        assertNotNull(validationMessage, "Please fill out the field.");
    }

    @Test
    public void openCharacterEditPage() {
        locationEditPage.openAsAuthorize(7);
        locationEditPage.openCharacterEdit();
        assertTrue(driver.getCurrentUrl().contains("http://localhost:8082/character/edit/"));
    }

    @Test
    public void deleteCharacterFromLocationTest() {
        int locationId = 5;
        locationEditPage.openAsAuthorize(locationId);
        String deleted = locationEditPage.deleteCharacter();
        assertEquals("http://localhost:8082/location/edit/" + locationId, driver.getCurrentUrl());
        boolean isDeletedInSelect = locationEditPage.getCharacterSelect().getOptions().stream()
                .anyMatch(option -> option.getText().equals(deleted));
        assertTrue(isDeletedInSelect);
    }

    @Test
    public void backToLocationsPageTest() {
        locationEditPage.openAsAuthorize(1);
        locationEditPage.backToLocations();
    }

}
