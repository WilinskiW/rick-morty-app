package com.rick_morty.rick_morty_ui.selenium.test.location;

import com.rick_morty.rick_morty_ui.selenium.page.location.LocationAddPage;
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
public class LocationAddPageTest {
    private final LocationAddPage locationAddPage;
    private final WebDriver driver;

    public LocationAddPageTest() {
        this.driver = new FirefoxDriver();
        this.locationAddPage = new LocationAddPage(driver);
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
        locationAddPage.openAsAuthorize();
        locationAddPage.openEpisodes();

        assertEquals("http://localhost:8082/episode/all", driver.getCurrentUrl());

    }

    @Test
    public void openLocations() {
        locationAddPage.openAsAuthorize();
        locationAddPage.openLocations();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

    @Test
    public void openLogin() {
        locationAddPage.openAsAuthorize();
        assertThrows(NoSuchElementException.class, () -> locationAddPage.openLogin());
    }

    @Test
    public void openRegister() {
        locationAddPage.openAsAuthorize();
        assertThrows(NoSuchElementException.class, () -> locationAddPage.openRegister());
    }

    @Test
    public void openAccountWhileAuthenticated() {
        locationAddPage.openAsAuthorize();
        locationAddPage.openAccount();

        assertEquals("http://localhost:8082/auth/account", driver.getCurrentUrl());
    }

    @Test
    public void openWhileNotAuthenticated() {
        locationAddPage.open();
        assertEquals("http://localhost:8082/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void openWhileNotAuthorized(){
        locationAddPage.openAsModerator();
        assertTrue(driver.getPageSource().contains("Access denied"));
    }

    @Test
    public void openHome() {
        locationAddPage.openAsAuthorize();
        locationAddPage.openHome();

        assertEquals("http://localhost:8082/", driver.getCurrentUrl());
    }

    @Test
    public void editLocation_addNewOneCharacter_andSubmitTest() {
        locationAddPage.openAsAuthorize();
        locationAddPage.fillTheForm("UNIT TEST","Planet","Test dimension");
        locationAddPage.selectOneCharacter();
        locationAddPage.saveButton();

        assertTrue(driver.getPageSource().contains("UNIT TEST"));
    }

    @Test
    public void editLocation_addFewNewCharacters_andSubmitTest() {
        locationAddPage.openAsAuthorize();
        locationAddPage.fillTheForm("UNIT TEST 2","Planet","Test dimension");
        locationAddPage.selectManyCharacters();

        locationAddPage.saveButton();

        assertTrue(driver.getPageSource().contains("UNIT TEST 2"));
    }

    @Test
    public void tryToSubmitFormWhileAtLeastOneFieldIsEmptyTest() {
        locationAddPage.openAsAuthorize();
        locationAddPage.fillTheForm("","Planet","Test dimension");
        locationAddPage.selectOneCharacter();
        locationAddPage.saveButton();

        assertEquals("http://localhost:8082/location/add", driver.getCurrentUrl());
        String validationMessage = locationAddPage.getNameInput().getAttribute("validationMessage");
        assertNotNull(validationMessage, "Please fill out the field.");
    }


    @Test
    public void backToLocations() {
        locationAddPage.openAsAuthorize();
        locationAddPage.openBackLink();

        assertEquals("http://localhost:8082/location/all", driver.getCurrentUrl());
    }

}
