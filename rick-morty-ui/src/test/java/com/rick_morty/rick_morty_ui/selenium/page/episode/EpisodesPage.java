package com.rick_morty.rick_morty_ui.selenium.page.episode;

import com.rick_morty.rick_morty_ui.selenium.utils.NavbarPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EpisodesPage extends NavbarPage {
    @FindBy(linkText = "View Details")
    private WebElement episodeLink;

    @FindBy(linkText = "Add new episode")
    private WebElement addNewEpisodeLink;

    public EpisodesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost:8082/episode/all");
    }

    public void openEpisodeDetails() {
        episodeLink.click();
    }

    public void openAddNewEpisode() {
        addNewEpisodeLink.click();
    }
}

