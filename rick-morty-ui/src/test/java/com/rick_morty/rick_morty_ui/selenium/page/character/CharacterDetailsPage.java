package com.rick_morty.rick_morty_ui.selenium.page.character;


import com.rick_morty.rick_morty_ui.selenium.page.NavbarPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CharacterDetailsPage extends NavbarPage {
    @FindBy(linkText = "Back")
    private WebElement backLink;

    @FindBy(css = "form[action*='/character/'] button[type='submit'].btn-danger")
    private WebElement deleteLink;

    @FindBy(css = "form[action*='/character/edit/'] button[type='submit']")
    private WebElement editLink;

    protected CharacterDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void open(int characterId) {
        driver.get("http://localhost:8082/character/" + characterId);
    }

    public void openBackLink() {
        backLink.click();
    }

    /**
     * @return Character name who was deleted
     */
    public String deleteCharacter() {
        String characterName = driver.findElement(By.tagName("h1")).getText();
        deleteLink.click();
        driver.switchTo().alert().accept();
        return characterName;
    }

    public void openEditLink() {
        editLink.click();
    }
}
