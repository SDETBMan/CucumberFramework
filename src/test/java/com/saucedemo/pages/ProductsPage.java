package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductsPage {
    private WebDriver driver;
    private WebDriverWait wait; // 1. Declare the Wait object

    private By title = By.className("title");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        // 2. Initialize the Wait (10 seconds max)
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isProductsPageDisplayed() {
        return driver.findElement(title).isDisplayed();
    }

    public String getPageTitle() {
        return driver.findElement(title).getText();
    }

    public void addToCart(String productName) {
        // Dynamic Locator strategy
        String idInfo = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");

        // Good practice: Wait for the button to be clickable before clicking
        wait.until(ExpectedConditions.elementToBeClickable(By.id(idInfo))).click();
    }

    public String getCartBadgeText() {
        // THIS IS THE FIX:
        // Instead of driver.findElement (which fails instantly if missing),
        // we use wait.until (which keeps looking for 10 seconds).
        WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
        return badge.getText();
    }

    public void clickCartIcon() {
        driver.findElement(cartLink).click();
    }
}
