package com.harel.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Takes screenshot on test failure into: target/screenshots/
 */
public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        try {
            var field = instance.getClass().getSuperclass().getDeclaredField("driver");
            field.setAccessible(true);
            WebDriver driver = (WebDriver) field.get(instance);

            if (driver == null) {
                return;
            }

            String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String name = result.getName() + "_" + ts + ".png";

            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), dir.resolve(name));

            System.out.println("[ScreenshotListener] Screenshot saved: " + dir.resolve(name));
        } catch (Exception e) {
            System.out.println("[ScreenshotListener] Failed taking screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onStart(ITestContext context) {
        // no-op
    }

    @Override
    public void onFinish(ITestContext context) {
        // no-op
    }
}
