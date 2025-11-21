package com.transactioninsights.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
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
    private ExtentTest test;

    // Locators
    private By dashboardTitle = By
            .xpath("//h1 | //h2 | //*[contains(text(),'Transaction') or contains(text(),'Dashboard')]");
    private By searchInput = By.cssSelector("input[placeholder*='Search'], input[type='search']");
    private By tableRows = By.cssSelector("tbody tr");
    private By tableElement = By.cssSelector("table");
    private By tableHeaders = By.cssSelector("thead th");
    private By successfulLinks = By.xpath("//a[contains(@class, 'text-green') or contains(text(), 'Successful')]");
    private By erroredLinks = By.xpath("//a[contains(@class, 'text-red') or contains(text(), 'Errored')]");
    private By modalDialog = By.cssSelector("[role='dialog'], .modal");
    private By autoRefreshToggle = By.cssSelector("button[role='switch']");
    private By columnFilters = By.cssSelector("thead input");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public DashboardPage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.test = test;
    }

    // Common logging methods
    private void logPass(String message) {
        if (test != null) {
            test.log(Status.PASS, "✓ " + message);
        }
    }

    private void logFail(String message) {
        if (test != null) {
            test.log(Status.FAIL, "✗ " + message);
        }
    }

    private void logInfo(String message) {
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    public boolean isDashboardLoaded() {
        try {
            logInfo("Checking if dashboard is loaded...");
            // Check if table is present as it's more reliable than title
            wait.until(ExpectedConditions.presenceOfElementLocated(tableElement));
            boolean isLoaded = driver.findElements(tableElement).size() > 0;
            if (isLoaded) {
                logPass("Dashboard loaded successfully");
            } else {
                logFail("Dashboard did not load - table not found");
            }
            return isLoaded;
        } catch (Exception e) {
            logFail("Dashboard failed to load: " + e.getMessage());
            return false;
        }
    }

    public String getPageTitle() {
        try {
            logInfo("Getting page title...");
            String title = driver.getTitle();
            logPass("Page title retrieved: " + title);
            return title;
        } catch (Exception e) {
            logFail("Failed to get page title: " + e.getMessage());
            return "";
        }
    }

    public boolean hasTitle() {
        try {
            logInfo("Checking if dashboard title exists...");
            boolean titleExists = driver.findElements(dashboardTitle).size() > 0;
            if (titleExists) {
                logPass("Dashboard title is present");
            } else {
                logFail("Dashboard title not found");
            }
            return titleExists;
        } catch (Exception e) {
            logFail("Error checking title: " + e.getMessage());
            return false;
        }
    }

    public List<WebElement> getTableHeaders() {
        try {
            logInfo("Retrieving table headers...");
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableHeaders));
            List<WebElement> headers = driver.findElements(tableHeaders);
            logPass("Retrieved " + headers.size() + " table headers");
            return headers;
        } catch (Exception e) {
            logFail("Failed to get table headers: " + e.getMessage());
            throw e;
        }
    }

    public int getTableHeaderCount() {
        try {
            logInfo("Counting table headers...");
            int count = getTableHeaders().size();
            logPass("Table has " + count + " headers");
            return count;
        } catch (Exception e) {
            logFail("Failed to count headers: " + e.getMessage());
            return 0;
        }
    }

    public boolean verifyTableColumnsExist(String... expectedColumns) {
        try {
            logInfo("Verifying table columns: " + String.join(", ", expectedColumns));
            List<WebElement> headers = getTableHeaders();
            for (String expectedColumn : expectedColumns) {
                boolean found = false;
                for (WebElement header : headers) {
                    if (header.getText().trim().contains(expectedColumn)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    logFail("Column not found: " + expectedColumn);
                    return false;
                }
            }
            logPass("All expected columns verified successfully");
            return true;
        } catch (Exception e) {
            logFail("Error verifying columns: " + e.getMessage());
            return false;
        }
    }

    public void searchTransaction(String query) {
        try {
            logInfo("Searching for: '" + query + "'");
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
            searchBox.clear();
            searchBox.sendKeys(query);
            Thread.sleep(500); // Wait for results to filter
            logPass("Search query entered successfully");
        } catch (Exception e) {
            logFail("Failed to search: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clearSearch() {
        try {
            logInfo("Clearing search filter...");
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
            searchBox.clear();
            Thread.sleep(500);
            logPass("Search filter cleared");
        } catch (Exception e) {
            logFail("Failed to clear search: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int getRowCount() {
        try {
            logInfo("Counting table rows...");
            int count = driver.findElements(tableRows).size();
            logPass("Table has " + count + " rows");
            return count;
        } catch (Exception e) {
            logFail("Failed to count rows: " + e.getMessage());
            return 0;
        }
    }

    public void clickFirstSuccessfulLink() {
        try {
            logInfo("Clicking first 'Successful' transaction link...");
            List<WebElement> links = driver.findElements(successfulLinks);
            if (!links.isEmpty()) {
                links.get(0).click();
                logPass("Clicked successful transactions link");
            } else {
                logFail("No successful transaction links found");
            }
        } catch (Exception e) {
            logFail("Failed to click successful link: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clickFirstErroredLink() {
        try {
            logInfo("Clicking first 'Errored' transaction link...");
            List<WebElement> links = driver.findElements(erroredLinks);
            if (!links.isEmpty()) {
                links.get(0).click();
                logPass("Clicked errored transactions link");
            } else {
                logFail("No errored transaction links found");
            }
        } catch (Exception e) {
            logFail("Failed to click errored link: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isModalDisplayed() {
        try {
            logInfo("Checking if modal is displayed...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog));
            logPass("Modal is displayed");
            return true;
        } catch (Exception e) {
            logFail("Modal not displayed: " + e.getMessage());
            return false;
        }
    }

    public void closeModal() {
        try {
            logInfo("Attempting to close modal...");
            // Method 1: Click close button
            List<WebElement> closeButtons = driver.findElements(By.xpath("//button[contains(text(), 'Close')]"));
            if (!closeButtons.isEmpty()) {
                closeButtons.get(0).click();
                logPass("Modal closed via Close button");
                return;
            }

            // Method 2: Press ESC key
            driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
            logPass("Modal closed via ESC key");

        } catch (Exception e) {
            logFail("Failed to close modal: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void toggleAutoRefresh() {
        try {
            logInfo("Toggling auto-refresh switch...");
            List<WebElement> toggles = driver.findElements(autoRefreshToggle);
            if (!toggles.isEmpty()) {
                toggles.get(0).click();
                logPass("Auto-refresh toggled");
            } else {
                logFail("Auto-refresh toggle not found");
            }
        } catch (Exception e) {
            logFail("Failed to toggle auto-refresh: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void enterColumnFilter(int columnIndex, String value) {
        try {
            logInfo("Entering filter value '" + value + "' in column " + columnIndex);
            List<WebElement> filters = driver.findElements(columnFilters);
            if (columnIndex < filters.size()) {
                filters.get(columnIndex).clear();
                filters.get(columnIndex).sendKeys(value);
                Thread.sleep(500);
                logPass("Column filter applied");
            } else {
                logFail("Column index " + columnIndex + " out of bounds");
            }
        } catch (Exception e) {
            logFail("Failed to enter column filter: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clearAllFilters() {
        try {
            logInfo("Clearing all column filters...");
            List<WebElement> filters = driver.findElements(columnFilters);
            for (WebElement filter : filters) {
                filter.clear();
            }
            logPass("All filters cleared (" + filters.size() + " filters)");
        } catch (Exception e) {
            logFail("Failed to clear filters: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
