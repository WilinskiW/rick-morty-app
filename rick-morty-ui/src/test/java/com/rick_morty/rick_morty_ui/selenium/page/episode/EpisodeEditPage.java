package com.rick_morty.rick_morty_ui.selenium.page.episode;

import com.rick_morty.rick_morty_ui.selenium.utils.NavbarPage;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class EpisodeEditPage extends NavbarPage {
    @FindBy(id = "title")
    @Getter
    private WebElement titleInput;

    @FindBy(id = "airDate")
    private WebElement airDateInput;

    @FindBy(id = "episode")
    private WebElement episodeCodeInput;

    @FindBy(id = "new-characters")
    private WebElement newCharactersElement;

    @FindBy(linkText = "Edit")
    private WebElement editButton;

    @FindBy(css = "form[action*='/episode/'] button[type='submit'].btn-danger")
    private WebElement deleteButton;

    @FindBy(linkText = "Back")
    private WebElement backLink;

    @FindBy(css = "button[type='submit'].btn-primary")
    private WebElement saveButton;

    public EpisodeEditPage(WebDriver driver) {
        super(driver);
    }

    public void open(int episodeId) {
        driver.get("http://localhost:8082/episode/edit/" + episodeId);
    }


    public void openAsAuthorize(int episodeId) {
        userLogger.loginAsModerator();
        open(episodeId);
    }

    public void fillTheEditForm(String title, String airDate, String episodeCode) {
        titleInput.clear();
        titleInput.sendKeys(title);
        airDateInput.clear();
        airDateInput.sendKeys(airDate);
        episodeCodeInput.clear();
        episodeCodeInput.sendKeys(episodeCode);
    }

    /**
     * @return Selected character
     */
    public String selectOneCharacter() {
        Select select = new Select(newCharactersElement);
        select.selectByIndex(0);
        return select.getAllSelectedOptions().getLast().getText();
    }

    public Select getCharacterSelect() {
        return new Select(newCharactersElement);
    }

    public List<String> selectManyCharacters() {
        Select select = getCharacterSelect();
        Actions action = new Actions(driver);

        Action seriesOfActions = action.keyDown(Keys.CONTROL)
                .click(select.getOptions().getFirst())
                .click(select.getOptions().getLast())
                .build();

        seriesOfActions.perform();

        return select.getAllSelectedOptions().stream()
                .map(option -> option.getText())
                .toList();
    }

    public void openCharacterEdit() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");
        editButton.click();
    }

    public String deleteCharacter() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");

        List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr"));

        for (WebElement row : rows) {
            String characterName = row.findElement(By.cssSelector("td:nth-child(2)")).getText();

            if (deleteButton != null) {
                System.out.println("Kliknięto Remove dla postaci: " + characterName);
                deleteButton.click();
                return characterName;
            }
        }
        return "";
    }

    public void submitEpisode() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='40%'");
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void backToEpisodes() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='40%'");
        backLink.click();
    }
}
