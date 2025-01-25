package com.rick_morty.rick_morty_ui.selenium.test.character;

import com.rick_morty.rick_morty_ui.selenium.page.character.CharacterEditPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
public class CharacterEditPageTest {
    private final CharacterEditPage characterEditPage;
    private final WebDriver driver;

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @BeforeEach
    public void cleanUp() {
        driver.manage().deleteAllCookies();
    }

    public CharacterEditPageTest() {
        this.driver = new FirefoxDriver();
        this.characterEditPage = new CharacterEditPage(driver);
    }

    @Test
    public void fillOutCharacterFormTest() {
        characterEditPage.openAsModerator(1);
        characterEditPage.fillOutTheForm("UNIT TEST");
        driver.get("http://localhost:8082/character/all");

        assertTrue(driver.getPageSource().contains("UNIT TEST"));
    }

    @Test
    public void checkAddNewLocationButtonVisibilityForModeratorRoleTest() {
        characterEditPage.openAsModerator(1);
        assertFalse(driver.getPageSource().contains("+ Add New Location"));
    }

    @Test
    public void checkAddNewLocationButtonVisibilityForAdminRoleTest() {
        characterEditPage.openAsAdmin(1);
        assertTrue(driver.getPageSource().contains("+ Add New Location"));
    }

    @Test
    public void fillFieldBlankAndSubmitTest() {
        characterEditPage.openAsModerator(1);
        characterEditPage.fillOutTheForm("");

        String validationMessage = characterEditPage.getNameInput().getAttribute("validationMessage");
        assertNotNull(validationMessage, "Please fill out the field.");
    }

    @Test
    public void goToAddNewLocationLinkTest(){
        characterEditPage.openAsAdmin(1);
        characterEditPage.openAddNewLocationLink();

        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

        assertEquals("http://localhost:8082/location/add", driver.getCurrentUrl());
    }

    @Test
    public void goBackTest() {
        characterEditPage.openAsAdmin(1);
        characterEditPage.openBackLink();

        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }
}
