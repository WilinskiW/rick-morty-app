package com.rick_morty.rick_morty_ui.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserLoggerUtil {
    private final WebDriver webDriver;

    @FindBy(id = "username")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(tagName = "button")
    private WebElement loginButton;

    public UserLoggerUtil(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    public void loginAsAdmin() {
        open();
        username.sendKeys("rick");
        password.sendKeys("123");
        loginButton.click();
    }

    public void loginAsModerator() {
        open();
        username.sendKeys("morty");
        password.sendKeys("123");
        loginButton.click();
    }

    private void open() {
        webDriver.get("http://localhost:8082/auth/login");
    }
}
