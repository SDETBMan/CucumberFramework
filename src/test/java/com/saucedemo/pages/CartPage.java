package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private WebDriver driver;

    // Locator for the item name in the cart list
    private By cartItemName = By.className("inventory_item_name");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // specific check: does the text match what we expect?
    public String getFirstItemName() {
        return driver.findElement(cartItemName).getText();
    }
}
