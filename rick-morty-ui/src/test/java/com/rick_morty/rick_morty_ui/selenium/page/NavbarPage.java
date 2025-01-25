package com.rick_morty.rick_morty_ui.selenium.page;

import com.rick_morty.rick_morty_ui.selenium.utils.UserLoggerUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class NavbarPage {
    protected final WebDriver driver;
    protected final UserLoggerUtil userLogger;

    @FindBy(linkText = "Home")
    private WebElement homeLink;

    @FindBy(linkText = "Characters")
    private WebElement charactersLink;

    @FindBy(linkText = "Locations")
    private WebElement locationsLink;

    @FindBy(linkText = "Episodes")
    private WebElement episodesLink;

    @FindBy(linkText = "Login")
    private WebElement loginLink;

    @FindBy(linkText = "Register")
    private WebElement registerLink;

    @FindBy(linkText = "Account")
    private WebElement accountLink;


    protected NavbarPage(WebDriver driver) {
        this.driver = driver;
        this.userLogger = new UserLoggerUtil(driver);
        PageFactory.initElements(driver, this);
    }

    public void openHome(){
        homeLink.click();
    }

    public void openCharacters(){
        charactersLink.click();
    }

    public void openLocations(){
        locationsLink.click();
    }

    public void openEpisodes(){
        episodesLink.click();
    }

    public void openLogin(){
        loginLink.click();
    }

    public void openRegister(){
        registerLink.click();
    }

    public void openAccount(){
        accountLink.click();
    }

    public void loginAsAdmin(){
        userLogger.loginAsAdmin();
    }

    public void loginAsModerator(){
        userLogger.loginAsModerator();
    }
}
