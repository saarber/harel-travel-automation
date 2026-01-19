package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPage extends BasePage {

    private static final String URL = "https://digital.harel-group.co.il/travel-policy";

    private final By firstTimePurchaseBtn = By.xpath("//button[contains(.,'לרכישה בפעם הראשונה')]");

    // Optional banners/popups (best-effort) - won't fail if not present
    private final By cookiesAcceptBtn = By.xpath("//button[contains(.,'אישור') or contains(.,'מאשר') or contains(.,'Accept')]");
    private final By closePopupBtn = By.xpath("//button[contains(.,'סגור') or contains(@aria-label,'סגור') or contains(@aria-label,'Close')]");

    public LandingPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.get(URL);
        // React app: wait for the "first purchase" button to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstTimePurchaseBtn));
        dismissPopupsIfAny();
    }

    public void clickFirstTimePurchase() {
        dismissPopupsIfAny();
        safeClick(firstTimePurchaseBtn);
    }

    private void dismissPopupsIfAny() {
        // cookie banner (best-effort)
        try {
            if (isPresent(cookiesAcceptBtn)) {
                safeClick(cookiesAcceptBtn);
            }
        } catch (Exception ignored) {
        }

        // close dialog popup (best-effort)
        try {
            if (isPresent(closePopupBtn)) {
                safeClick(closePopupBtn);
            }
        } catch (Exception ignored) {
        }
    }
}
