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
        String target = ConfigReader.getProperty("target", System.getProperty("target", "local")).toLowerCase();

        if (target.equals("browserstack")) {
            initBrowserStack();
        } else {
            initLocalDriver();
        }
    }

    private static void initLocalDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless", "false"))
                || Boolean.parseBoolean(System.getProperty("headless", "false"))
                || System.getenv("CI") != null;

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        driver.set(new ChromeDriver(options));
        System.out.println("--- Started Local Chrome Driver (headless=" + headless + ") ---");
    }

    private static void initBrowserStack() {
        String username = ConfigReader.getProperty("bs_user");
        String accessKey = ConfigReader.getProperty("bs_key");

        if (username == null || username.isEmpty() || accessKey == null || accessKey.isEmpty()) {
            throw new RuntimeException("BrowserStack credentials not found! Set BS_USER and BS_KEY secrets or pass -Dbs_user=... -Dbs_key=...");
        }

        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        bstackOptions.put("sessionName", "CucumberFramework Build");
        options.setCapability("bstack:options", bstackOptions);

        try {
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
