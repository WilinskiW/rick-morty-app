package com.rick_morty.rick_morty_ui.selenium.test.character;

import com.rick_morty.rick_morty_ui.selenium.page.character.CharacterAddPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:reset_data.sql")
public class CharacterAddPageTest {
    private final CharacterAddPage characterAddPage;
    private final WebDriver driver;

    public CharacterAddPageTest() {
        this.driver = new FirefoxDriver();
        this.characterAddPage = new CharacterAddPage(driver);
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
    public void openAddCharacterPageTest() {
        characterAddPage.openAsAuthorize();

        assertEquals("http://localhost:8082/character/add", driver.getCurrentUrl());
    }

    @Test
    public void openAddCharacterPageWithNoAuthorizationTest() {
        characterAddPage.loginAsModerator();
        characterAddPage.open();

        driver.get("http://localhost:8082/character/add");

        assertTrue(driver.getPageSource().contains("Access denied"));
    }

    @Test
    public void fillOutCharacterFormTest() {
        characterAddPage.openAsAuthorize();
        characterAddPage.fillOutTheForm("UNIT TEST");

        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains("UNIT TEST"));
    }

    @Test
    //Failure might happen, when running all tests. Single run won't cause failure.
    public void openAddLocationLinkTest() {
        characterAddPage.openAsAuthorize();
        characterAddPage.openAddNewLocationLink();

        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

        assertEquals("http://localhost:8082/location/add", driver.getCurrentUrl());
    }

    @Test
    public void goBackTest() {
        characterAddPage.openAsAuthorize();
        characterAddPage.openBackLink();
        assertEquals("http://localhost:8082/character/all", driver.getCurrentUrl());
    }

    @Test
    public void tryToFillOutCharacterFormWithBlankNameTest() {
        characterAddPage.loginAsAdmin();
        characterAddPage.open();
        characterAddPage.fillOutTheForm("");

        String validationMessage = characterAddPage.getNameInput().getAttribute("validationMessage");
        assertNotNull(validationMessage, "Please fill out the field.");
    }
}
