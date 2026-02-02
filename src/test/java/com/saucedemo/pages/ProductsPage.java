package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage {
    private WebDriver driver;

    private By title = By.className("title");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProductsPageDisplayed() {
        return driver.findElement(title).isDisplayed();
    }

    public String getPageTitle() {
        return driver.findElement(title).getText();
    }

    // New Locators
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");

    // Action: Add a specific product (Dynamic Locator!)
    public void addToCart(String productName) {
        // This converts "Sauce Labs Backpack" into "add-to-cart-sauce-labs-backpack"
        String idInfo = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        driver.findElement(By.id(idInfo)).click();
    }

    public String getCartBadgeText() {
        return driver.findElement(cartBadge).getText();
    }

    public void clickCartIcon() {
        driver.findElement(cartLink).click();
    }
}
