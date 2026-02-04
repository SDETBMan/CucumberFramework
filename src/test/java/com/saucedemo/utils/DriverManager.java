package com.saucedemo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    private static void initializeDriver() {
        // 1. Check if we want to run locally or on BrowserStack
        // Default to "local" if no property is provided
        String target = System.getProperty("target", "local").toLowerCase();

        if (target.equals("browserstack")) {
            initBrowserStack();
        } else {
            initLocalDriver();
        }
    }

    private static void initLocalDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        // options.addArguments("--headless=new"); // Uncomment if you want headless locally
        driver.set(new ChromeDriver(options));
        System.out.println("--- Started Local Chrome Driver ---");
    }

    private static void initBrowserStack() {
        // 1. Get credentials from System Properties (passed via Maven command line)
        String username = System.getProperty("bsUser");
        String accessKey = System.getProperty("bsKey");

        if (username == null || accessKey == null) {
            throw new RuntimeException("BrowserStack credentials not found! Pass -DbsUser=... and -DbsKey=...");
        }

        // 2. Configure BrowserStack Options
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        bstackOptions.put("sessionName", "Cucumber Framework Build");
        options.setCapability("bstack:options", bstackOptions);

        try {
            // 3. Connect to the Cloud Grid
            String url = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
            driver.set(new RemoteWebDriver(new URL(url), options));
            System.out.println("--- Started BrowserStack Remote Driver ---");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid BrowserStack URL", e);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
