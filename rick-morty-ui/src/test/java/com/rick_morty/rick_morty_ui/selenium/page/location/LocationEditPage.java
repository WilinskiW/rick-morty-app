package com.rick_morty.rick_morty_ui.selenium.page.location;

import com.rick_morty.rick_morty_ui.selenium.utils.NavbarPage;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class LocationEditPage extends NavbarPage {
    @FindBy(id = "name")
    @Getter
    private WebElement nameInput;

    @FindBy(id = "type")
    private WebElement typeInput;

    @FindBy(id = "dimension")
    private WebElement dimensionInput;

    @FindBy(id = "new-characters")
    private WebElement newCharactersElement;

    @FindBy(linkText = "Edit")
    private WebElement editButton;

    @FindBy(css = "form[action*='/location/'] button[type='submit'].btn-danger")
    private WebElement deleteButton;

    @FindBy(linkText = "Back")
    private WebElement backLink;

    @FindBy(css = "input[type='submit'].btn-primary")
    private WebElement saveButton;

    public LocationEditPage(WebDriver driver) {
        super(driver);
    }

    public void open(int locationId) {
        driver.get("http://localhost:8082/location/edit/" + locationId);
    }


    public void openAsAuthorize(int locationId) {
        userLogger.loginAsModerator();
        open(locationId);
    }

    public void fillTheEditForm(String name, String type, String input) {
        nameInput.clear();
        nameInput.sendKeys(name);
        typeInput.clear();
        typeInput.sendKeys(type);
        dimensionInput.clear();
        dimensionInput.sendKeys(input);
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='70%'");

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
        js.executeScript("document.body.style.zoom='70%'");
        editButton.click();
    }

    public String deleteCharacter() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='60%'");

        List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr"));

        for (WebElement row : rows) {
            String characterName = row.findElement(By.cssSelector("td:nth-child(2)")).getText();

            if (deleteButton != null) {
                deleteButton.click();
                return characterName;
            }
        }
        return "";
    }

    public void submitLocation() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='65%'");
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void backToLocations() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='40%'");
        backLink.click();
    }
}
