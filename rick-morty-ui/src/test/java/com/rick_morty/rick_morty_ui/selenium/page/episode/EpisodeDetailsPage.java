package com.rick_morty.rick_morty_ui.selenium.page.episode;

import com.rick_morty.rick_morty_ui.selenium.utils.NavbarPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EpisodeDetailsPage extends NavbarPage {
    @FindBy(linkText = "Back")
    private WebElement backLink;

    @FindBy(css = "form[action*='/episode/'] button[type='submit'].btn-danger")
    private WebElement deleteLink;

    @FindBy(css = "form[action*='/episode/edit/'] button[type='submit']")
    private WebElement editLink;

    public EpisodeDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void open(int episodeId) {
        driver.get("http://localhost:8082/episode/" + episodeId);
    }

    public void openBackLink() {
        backLink.click();
    }

    /**
     * @return Episode name which was deleted
     */
    public String deleteEpisode() {
        String episodeName = driver.findElement(By.tagName("h1")).getText();
        deleteLink.click();
        driver.switchTo().alert().accept();
        driver.navigate().refresh();
        return episodeName;
    }

    public void openEditLink() {
        editLink.click();
    }
}
