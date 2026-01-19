package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContinentPage extends BasePage {

    // Step 4 button
    private final By nextToDatesBtn = By.xpath("//button[contains(.,'הלאה לבחירת תאריכי הנסיעה')]");

    public ContinentPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        // Make sure we are on the continent selection step
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(nextToDatesBtn),
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(.,'יבשת') or contains(.,'בחר')]")
                )
        ));
    }

    public void chooseContinent(String continentHebrew) {
        // Example values: "אירופה" / "אסיה" / "אמריקה" / "אפריקה" / "אוקיאניה"
        clickByText(continentHebrew);
    }

    public void clickNextToTravelDates() {
        safeClick(nextToDatesBtn);
    }
}
