package com.rick_morty.rick_morty_ui.selenium.page.character;

import com.rick_morty.rick_morty_ui.selenium.utils.UserLoggerUtil;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CharactersPageTest {
    private final CharactersPage charactersPage;
    private final UserLoggerUtil userLogger;

    public CharactersPageTest() {
        WebDriver webDriver = new FirefoxDriver();
        this.charactersPage = new CharactersPage(webDriver);
        this.userLogger = new UserLoggerUtil(webDriver);
    }

    @Test
    public void backToHomeTest(){
        charactersPage.open();
        charactersPage.backToHome();
    }

    @Test
    public void openCharacterDetailsTest(){
        charactersPage.open();
        charactersPage.openCharacterDetails();
    }

    @Test
    public void openEpisodesTest(){
        charactersPage.open();
        charactersPage.openEpisodes();
    }

    @Test
    public void openLocationsTest(){
        charactersPage.open();
        charactersPage.openLocations();
    }

    @Test
    public void openLoginTest(){
        charactersPage.open();
        charactersPage.openLogin();
    }

    @Test
    public void openRegisterTest(){
        charactersPage.open();
        charactersPage.openRegister();
    }

    @Test
    public void openFiveMinutesCharacterTest(){
        charactersPage.open();
        charactersPage.openFiveMinutesCharacterDetails();
    }

    @Test
    public void openAddNewCharacterTest(){
        userLogger.loginAsAdmin();
        charactersPage.open();
        charactersPage.openAddNewCharacter();
    }

    @Test
    public void openAccountTest(){
        userLogger.loginAsAdmin();
        charactersPage.open();
        charactersPage.openAccount();
    }

}
