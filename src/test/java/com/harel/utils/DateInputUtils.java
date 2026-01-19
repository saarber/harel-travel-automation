package com.harel.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Most date pickers allow typing. This helper tries keyboard first, then JS injection with proper events.
 * This approach is usually more stable than clicking inside a dynamic date picker grid.
 */
public class DateInputUtils {

    private static final DateTimeFormatter UI_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void setDate(WebDriver driver, WebElement input, LocalDate date) {
        String value = date.format(UI_FORMAT);

        try {
            input.click();
            input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            input.sendKeys(value);
            input.sendKeys(Keys.ENTER);
            return;
        } catch (Exception ignored) {
        }

        // Fallback: JS set value + fire events
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                    input, value
            );

            // blur to close picker if needed
            js.executeScript("arguments[0].blur();", input);
        } catch (Exception e) {
            throw new RuntimeException("Failed setting date in input to: " + value, e);
        }
    }
}
