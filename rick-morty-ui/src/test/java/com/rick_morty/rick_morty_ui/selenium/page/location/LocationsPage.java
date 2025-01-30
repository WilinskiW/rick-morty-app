package com.rick_morty.rick_morty_ui.selenium.page.location;

import com.rick_morty.rick_morty_ui.selenium.utils.NavbarPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LocationsPage extends NavbarPage {
    @FindBy(linkText = "View Details")
    private WebElement locationDetailsLink;

    @FindBy(linkText = "Add new location")
    private WebElement addNewLocationLink;

    public LocationsPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost:8082/location/all");
    }

    public void openLocationDetails() {
        locationDetailsLink.click();
    }

    public void openAddNewLocation() {
        addNewLocationLink.click();
    }
}
