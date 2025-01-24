package com.rick_morty.rick_morty_ui.selenium.page.character;

import com.rick_morty.rick_morty_ui.selenium.utils.UserLoggerUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CharactersPage {
    private final WebDriver webDriver;
    private final UserLoggerUtil userLogger;

    @FindBy(linkText = "Home")
    private WebElement homeLink;

    @FindBy(linkText = "Locations")
    private WebElement locationsLink;

    @FindBy(linkText = "Episodes")
    private WebElement episodesLink;

    @FindBy(linkText = "View details")
    private WebElement characterLink;

    @FindBy(linkText = "5 minutes character")
    private WebElement fiveMinutesCharacterLink;

    @FindBy(linkText = "Login")
    private WebElement loginLink;

    @FindBy(linkText = "Register")
    private WebElement registerLink;

    @FindBy(linkText = "Add new character")
    private WebElement addNewCharacterLink;

    @FindBy(linkText = "Account")
    private WebElement accountLink;

    public CharactersPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.userLogger = new UserLoggerUtil(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void open(){
        webDriver.get("http://localhost:8082/character/all");
    }

    public void backToHome(){
        homeLink.click();
    }

    public void openLocations(){
        locationsLink.click();
    }

    public void openEpisodes(){
        episodesLink.click();
    }

    public void openCharacterDetails(){
        characterLink.click();
    }

    public void openFiveMinutesCharacterDetails(){
        fiveMinutesCharacterLink.click();
    }

    public void openLogin(){
        loginLink.click();
    }

    public void openRegister(){
        registerLink.click();
    }

    public void openAddNewCharacter(){
        addNewCharacterLink.click();
    }

    public void openAccount(){
        accountLink.click();
    }
}
