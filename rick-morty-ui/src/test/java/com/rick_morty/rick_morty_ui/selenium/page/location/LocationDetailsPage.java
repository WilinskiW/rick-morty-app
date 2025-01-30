package com.rick_morty.rick_morty_ui.selenium.page.location;

import com.rick_morty.rick_morty_ui.selenium.utils.NavbarPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LocationDetailsPage extends NavbarPage {
    @FindBy(linkText = "Back")
    private WebElement backLink;

    @FindBy(css = "form[action*='/location/'] button[type='submit'].btn-danger")
    private WebElement deleteLink;

    @FindBy(css = "form[action*='/location/edit/'] button[type='submit']")
    private WebElement editLink;

    public LocationDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void open(int locationId) {
        driver.get("http://localhost:8082/location/" + locationId);
    }

    public void openBackLink() {
        backLink.click();
    }

    /**
     * @return Location name which was deleted
     */
    public String deleteLocation() {
        String locationName = driver.findElement(By.tagName("h1")).getText();
        deleteLink.click();
        driver.switchTo().alert().accept();
        driver.navigate().refresh();
        return locationName;
    }

    public void openEditLink() {
        editLink.click();
    }
}
