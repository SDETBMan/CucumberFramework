package com.saucedemo.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    // ThreadLocal isolates the WebDriver instance for each thread
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();

            // Setup "Ghost Mode" options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new"); // Run without a window
            options.addArguments("--window-size=1920,1080"); // "See" the whole page
            options.addArguments("--disable-search-engine-choice-screen"); // Block that annoying popup

            // Pass the options into the driver
            driver.set(new ChromeDriver(options));
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Vital: Clean up the thread memory
        }
    }
}
