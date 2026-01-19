package com.harel.pages;

import com.harel.utils.DateInputUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TravelDatesPage extends BasePage {

    private final By departureInput = By.xpath("//input[contains(@placeholder,'יציאה') or contains(@aria-label,'יציאה')]");
    private final By returnInput = By.xpath("//input[contains(@placeholder,'חזרה') or contains(@aria-label,'חזרה')]");

    private final By nextToPassengersBtn = By.xpath("//button[contains(.,'הלאה לפרטי הנוסעים')]");

    // Label might be: סה"כ ימים / סה״כ ימים
    private final By totalDaysLabel = By.xpath("//*[contains(normalize-space(),'סה') and contains(normalize-space(),'ימים')]");

    public TravelDatesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(nextToPassengersBtn));
    }

    public void setDepartureDate(LocalDate date) {
        WebElement input = findDepartureInput();
        DateInputUtils.setDate(driver, input, date);
    }

    public void setReturnDate(LocalDate date) {
        WebElement input = findReturnInput();
        DateInputUtils.setDate(driver, input, date);
    }

    public int getTotalDays() {
        // Try to read from a container near label
        WebElement label = waitVisible(totalDaysLabel);
        String textBlock;

        try {
            textBlock = label.findElement(By.xpath("ancestor::*[self::div or self::section][1]")).getText();
        } catch (Exception e) {
            textBlock = label.getText();
        }

        // Extract first integer from the block
        Matcher m = Pattern.compile("(\\d+)").matcher(textBlock);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }

        // Fallback: search in next siblings
        try {
            String siblingText = label.findElement(By.xpath("following::*[1]")).getText();
            Matcher m2 = Pattern.compile("(\\d+)").matcher(siblingText);
            if (m2.find()) {
                return Integer.parseInt(m2.group(1));
            }
        } catch (Exception ignored) {
        }

        throw new AssertionError("לא הצלחתי לקרוא את סה\"כ ימים מהמסך (Total Days)");
    }

    public void clickNextToPassengers() {
        safeClick(nextToPassengersBtn);
    }

    private WebElement findDepartureInput() {
        // Prefer label-based, then placeholder-based
        By byLabel = By.xpath("//*[contains(normalize-space(),'תאריך יציאה')]/ancestor::*[self::div or self::label][1]//input");
        if (isPresent(byLabel)) {
            return waitVisible(byLabel);
        }
        return waitVisible(departureInput);
    }

    private WebElement findReturnInput() {
        By byLabel = By.xpath("//*[contains(normalize-space(),'תאריך חזרה')]/ancestor::*[self::div or self::label][1]//input");
        if (isPresent(byLabel)) {
            return waitVisible(byLabel);
        }
        return waitVisible(returnInput);
    }
}
