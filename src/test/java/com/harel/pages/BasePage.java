package com.harel.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        waitClickable(locator).click();
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void safeClick(By locator) {
        try {
            click(locator);
        } catch (ElementClickInterceptedException | TimeoutException e) {
            WebElement el = waitClickable(locator);
            jsClick(el);
        }
    }

    protected boolean isPresent(By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            return !driver.findElements(locator).isEmpty();
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        }
    }

    /**
     * Click by visible Hebrew text. Useful for dynamic/react UI.
     */
    protected void clickByText(String text) {
        By locator = By.xpath("//div[contains(text(), '" + text + "')]");
        WebElement el = waitVisible(locator);

        // If element itself isn't clickable, click a clickable ancestor
        try {
            el.click();
            return;
        } catch (Exception ignored) {
        }

        try {
            WebElement ancestorButton = el.findElement(By.xpath("ancestor::button[1]"));
            jsClick(ancestorButton);
            return;
        } catch (Exception ignored) {
        }

        // fallback: JS click on the element itself
        jsClick(el);
    }
}
