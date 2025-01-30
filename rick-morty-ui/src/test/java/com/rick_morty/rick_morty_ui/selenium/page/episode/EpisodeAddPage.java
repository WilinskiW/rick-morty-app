package com.rick_morty.rick_morty_ui.selenium.page.episode;

import com.rick_morty.rick_morty_ui.selenium.utils.NavbarPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EpisodeAddPage extends NavbarPage {
    @FindBy(id = "title")
    @Getter
    private WebElement titleInput;

    @FindBy(id = "air-date")
    private WebElement airDateInput;

    @FindBy(id = "episode-code")
    private WebElement episodeCodeInput;


    @FindBy(linkText = "Back to Episodes")
    private WebElement backLink;

    @FindBy(css = "button[type='submit'].btn-primary")
    private WebElement saveButton;


    public EpisodeAddPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost:8082/episode/add");
    }

    public void openAsAuthorize(){
        userLogger.loginAsAdmin();
        open();
    }

    public void fillOutTheForm(String title) {
        titleInput.sendKeys(title);
        airDateInput.sendKeys("January 29, 2020");
        episodeCodeInput.sendKeys("S05E13");

        saveButton.click();
    }

    public void openBackLink() {
        backLink.click();
    }

    public void openSaveButton() {
        saveButton.click();
    }
}
