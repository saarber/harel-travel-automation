package com.harel.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsActions {

    private final WebDriver driver;

    public JsActions(WebDriver driver) {
        this.driver = driver;
    }

    public void click(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void setInputValueAndDispatch(WebElement input, String value) {
        String script = "arguments[0].value = arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));";
        ((JavascriptExecutor) driver).executeScript(script, input, value);
    }
}
