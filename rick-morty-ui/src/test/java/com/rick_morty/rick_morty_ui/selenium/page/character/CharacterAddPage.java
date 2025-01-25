package com.rick_morty.rick_morty_ui.selenium.page.character;

import com.rick_morty.rick_morty_ui.selenium.utils.NavbarPage;
import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CharacterAddPage extends NavbarPage {
    @Getter
    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "status")
    private WebElement statusElement;

    @FindBy(id = "species")
    private WebElement speciesInput;

    @FindBy(id = "gender")
    private WebElement genderElement;

    @FindBy(id = "origin")
    private WebElement originElement;

    @FindBy(id = "currentLocation")
    private WebElement currentLocationElement;

    @FindBy(linkText = "+ Add New Location")
    private WebElement addNewLocationLink;

    @FindBy(id = "imageUrl")
    private WebElement imageUrlInput;

    @FindBy(linkText = "Back to Characters")
    private WebElement backLink;

    @FindBy(css = "button[type='submit']")
    private WebElement saveButton;

    public CharacterAddPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost:8082/character/add");
    }

    public void openAsAuthorize() {
        loginAsAdmin();
        open();
    }

    public void fillOutTheForm(String characterName) {
        nameInput.sendKeys(characterName);

        Select statusSelect = new Select(statusElement);
        Select genderSelect = new Select(genderElement);
        Select originSelect = new Select(originElement);
        Select currentLocationSelect = new Select(currentLocationElement);

        statusSelect.selectByVisibleText("Alive");
        speciesInput.sendKeys("Human");
        genderSelect.selectByVisibleText("Male");
        originSelect.selectByVisibleText("Citadel of Ricks");
        currentLocationSelect.selectByVisibleText("Abadango");

        wait.until(ExpectedConditions.elementToBeClickable(imageUrlInput))
                .sendKeys("https://rickandmortyapi.com/api/character/avatar/1.jpeg");
        saveButton.click();
    }


    public void openAddNewLocationLink() {
        addNewLocationLink.click();
    }

    public void openBackLink() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='50%'");
        wait.until(ExpectedConditions.elementToBeClickable(backLink)).click();
    }
}
