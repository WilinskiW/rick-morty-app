package com.rick_morty.rick_morty_ui.selenium.page.character;

import com.rick_morty.rick_morty_ui.selenium.page.NavbarPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CharactersPage extends NavbarPage {
    @FindBy(linkText = "View details")
    private WebElement characterLink;

    @FindBy(linkText = "5 minutes character")
    private WebElement fiveMinutesCharacterLink;

    @FindBy(linkText = "Add new character")
    private WebElement addNewCharacterLink;

    protected CharactersPage(WebDriver driver) {
        super(driver);
    }

    public void open(){
        driver.get("http://localhost:8082/character/all");
    }

    public void openCharacterDetails(){
        characterLink.click();
    }

    public void openFiveMinutesCharacterDetails(){
        fiveMinutesCharacterLink.click();
    }

    public void openAddNewCharacter(){
        addNewCharacterLink.click();
    }
}
