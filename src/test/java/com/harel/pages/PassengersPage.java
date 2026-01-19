package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PassengersPage extends BasePage {

    private final By passengersHeader = By.xpath("//*[contains(.,'פרטי הנוסעים')]");

    public PassengersPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isOpen() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(passengersHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
