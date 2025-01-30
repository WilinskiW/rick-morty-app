package com.rick_morty.rick_morty_ui.selenium.page.location;

import com.rick_morty.rick_morty_ui.selenium.utils.NavbarPage;
import lombok.Getter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class LocationAddPage extends NavbarPage {
    @FindBy(id = "name")
    @Getter
    private WebElement nameInput;

    @FindBy(id = "type")
    private WebElement typeInput;

    @FindBy(id = "dimension")
    private WebElement dimension;

    @FindBy(id = "residents")
    private WebElement residentsElement;

    @FindBy(linkText = "Back to Locations")
    private WebElement backLink;

    @FindBy(css = "button[type='submit'].btn-primary")
    private WebElement saveButton;


    public LocationAddPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost:8082/location/add");
    }

    public void openAsModerator(){
        userLogger.loginAsModerator();
        open();
    }

    public void openAsAuthorize(){
        userLogger.loginAsAdmin();
        open();
    }

    public void fillTheForm(String name, String type, String dimension) {
        this.nameInput.sendKeys(name);
        this.typeInput.sendKeys(type);
        this.dimension.sendKeys(dimension);
    }

    public void selectOneCharacter() {
        Select select = new Select(residentsElement);
        select.selectByIndex(0);
        select.getAllSelectedOptions().getLast().getText();
    }

    public Select getCharacterSelect() {
        return new Select(residentsElement);
    }

    public void selectManyCharacters() {
        Select select = getCharacterSelect();
        Actions action = new Actions(driver);

        Action seriesOfActions = action.keyDown(Keys.CONTROL)
                .click(select.getOptions().getFirst())
                .click(select.getOptions().getLast())
                .build();

        seriesOfActions.perform();
    }

    public void openBackLink() {
        backLink.click();
    }

    public void saveButton() {
        saveButton.click();
    }
}
