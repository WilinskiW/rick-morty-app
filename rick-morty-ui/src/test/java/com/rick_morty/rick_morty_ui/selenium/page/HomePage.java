package com.rick_morty.rick_morty_ui.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final WebDriver webDriver;

    @FindBy(id = "characters")
    private WebElement charactersLink;

    @FindBy(id = "locations")
    private WebElement locationsLink;

    @FindBy(id = "episodes")
    private WebElement episodesLink;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void open() {
        webDriver.get("http://localhost:8082/");
    }

    public void openCharacters() {
        charactersLink.click();
    }

    public void openLocations() {
        locationsLink.click();
    }

    public void openEpisodes() {
        episodesLink.click();
    }

}
