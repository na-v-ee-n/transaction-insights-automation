<<<FILE>>>
package com.transactioninsights.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By tableHeadersLocator = By.cssSelector("table thead th");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean verifyTableColumnsExist(String... expectedColumns) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableHeadersLocator));
        List<WebElement> headers = driver.findElements(tableHeadersLocator);
        if (headers.size() < expectedColumns.length) {
            return false;
        }
        for (String expectedColumn : expectedColumns) {
            boolean found = headers.stream()
                    .anyMatch(header -> header.getText().trim().equals(expectedColumn));
            if (!found) {
                return false;
            }
        }
        return true;
    }

    // Other methods omitted for brevity
}
<<<END>>>