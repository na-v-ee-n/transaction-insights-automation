package com.transactioninsights.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.transactioninsights.config.TestConfig;
import com.transactioninsights.exceptions.ElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private By successfulLinks = By.xpath("//tbody/tr/td[4]//button[1]");
    private By erroredLinks = By.xpath("//tbody/tr/td[6]//button[1]");
    private By modalDialog = By.cssSelector("[role='dialog'], .modal");
    private By autoRefreshToggle = By.cssSelector("button[role='switch']");
    private By columnFilters = By.cssSelector("thead input");
    private By commentInput = By.cssSelector("textarea.comment-input"); // Assuming a textarea for comments
    private By commentSubmitButton = By.cssSelector("button.submit-comment"); // Assuming a submit button for comments

    public DashboardPage(WebDriver driver) {
        this(driver, null);
    }

    public DashboardPage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getExplicitWait()));
        this.test = test;
    }

    // Common logging methods
    private void logFail(String message) {
        if (test != null) {
            test.log(Status.FAIL, "✗ " + message);
        }
    }

    public boolean isDashboardLoaded() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(tableElement));
            return driver.findElements(tableElement).size() > 0;
        } catch (Exception e) {
            logFail("Dashboard failed to load: " + e.getMessage());
            return false;
        }
    }

    public String getPageTitle() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            logFail("Failed to get page title: " + e.getMessage());
            return "";
        }
    }

    public boolean hasTitle() {
        try {
            return driver.findElements(dashboardTitle).size() > 0;
        } catch (Exception e) {
            logFail("Error checking title: " + e.getMessage());
            return false;
        }
    }

    public List<WebElement> getTableHeaders() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableHeaders));
            return driver.findElements(tableHeaders);
        } catch (Exception e) {
            logFail("Failed to get table headers: " + e.getMessage());
            throw new ElementNotFoundException("Table headers not found", e);
        }
    }

    public int getTableHeaderCount() {
        try {
            return getTableHeaders().size();
        } catch (Exception e) {
            logFail("Failed to count headers: " + e.getMessage());
            return 0;
        }
    }

    public boolean verifyTableColumnsExist(String... expectedColumns) {
        try {
            Set<String> actualColumns = getTableHeaders().stream()
                    .map(header -> header.getText().trim())
                    .collect(Collectors.toSet());

            for (String expectedColumn : expectedColumns) {
                boolean found = actualColumns.stream()
                        .anyMatch(col -> col.contains(expectedColumn));
                if (!found) {
                    logFail("Column not found: " + expectedColumn);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logFail("Error verifying columns: " + e.getMessage());
            return false;
        }
    }

    public DashboardPage sortColumn(String columnName) {
        return sortColumn(columnName, false);
    }

    public DashboardPage sortColumn(String columnName, boolean descending) {
        try {
            List<WebElement> headers = getTableHeaders();
            for (WebElement header : headers) {
                if (header.getText().trim().contains(columnName)) {
                    header.click();
                    if (descending) {
                        header.click();
                    }
                    return this;
                }
            }
            logFail("Column header not found: " + columnName);
            throw new ElementNotFoundException("Column header not found: " + columnName);
        } catch (Exception e) {
            logFail("Failed to sort column: " + e.getMessage());
            throw new ElementNotFoundException("Failed to sort column", e);
        }
    }

    public DashboardPage searchTransaction(String query) {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
            searchBox.clear();
            searchBox.sendKeys(query);
            wait.until(driver -> {
                List<WebElement> rows = driver.findElements(tableRows);
                return rows.isEmpty() || !rows.get(0).getText().isEmpty();
            });
            return this;
        } catch (Exception e) {
            logFail("Failed to search: " + e.getMessage());
            throw new ElementNotFoundException("Search failed", e);
        }
    }

    public DashboardPage clearSearch() {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
            searchBox.clear();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));
            return this;
        } catch (Exception e) {
            logFail("Failed to clear search: " + e.getMessage());
            throw new ElementNotFoundException("Clear search failed", e);
        }
    }

    public int getRowCount() {
        try {
            return driver.findElements(tableRows).size();
        } catch (Exception e) {
            logFail("Failed to count rows: " + e.getMessage());
            return 0;
        }
    }

    public DashboardPage clickFirstSuccessfulLink() {
        try {
            // Wait for at least one clickable successful link to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(successfulLinks));

            List<WebElement> links = driver.findElements(successfulLinks);
            if (links.isEmpty()) {
                logFail("No successful transaction links found");
                throw new ElementNotFoundException("No successful transaction links found");
            }

            // Find the first displayed and enabled link
            WebElement clickableLink = links.stream()
                    .filter(WebElement::isDisplayed)
                    .filter(WebElement::isEnabled)
                    .findFirst()
                    .orElseThrow(() -> new ElementNotFoundException("No clickable successful transaction links found"));

            // Wait for the element to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(clickableLink));
            clickableLink.click();
            return this;
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logFail("Failed to click successful link: " + e.getMessage());
            throw new ElementNotFoundException("Failed to click successful link", e);
        }
    }

    public DashboardPage clickFirstErroredLink() {
        try {
            // Wait for at least one clickable errored link to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(erroredLinks));

            List<WebElement> links = driver.findElements(erroredLinks);
            if (links.isEmpty()) {
                logFail("No errored transaction links found");
                throw new ElementNotFoundException("No errored transaction links found");
            }

            // Find the first displayed and enabled link
            WebElement clickableLink = links.stream()
                    .filter(WebElement::isDisplayed)
                    .filter(WebElement::isEnabled)
                    .findFirst()
                    .orElseThrow(() -> new ElementNotFoundException("No clickable errored transaction links found"));

            // Wait for the element to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(clickableLink));
            clickableLink.click();
            return this;
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logFail("Failed to click errored link: " + e.getMessage());
            throw new ElementNotFoundException("Failed to click errored link", e);
        }
    }

    public boolean isModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog));
            return true;
        } catch (Exception e) {
            logFail("Modal not displayed: " + e.getMessage());
            return false;
        }
    }

    public DashboardPage closeModal() {
        try {
            List<WebElement> closeButtons = driver.findElements(By.xpath("//button[contains(text(), 'Close')]"));
            if (!closeButtons.isEmpty()) {
                closeButtons.get(0).click();
                return this;
            }
            driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
            return this;
        } catch (Exception e) {
            logFail("Failed to close modal: " + e.getMessage());
            throw new ElementNotFoundException("Failed to close modal", e);
        }
    }

    public DashboardPage toggleAutoRefresh() {
        try {
            List<WebElement> toggles = driver.findElements(autoRefreshToggle);
            if (toggles.isEmpty()) {
                logFail("Auto-refresh toggle not found");
                throw new ElementNotFoundException("Auto-refresh toggle not found");
            }
            toggles.get(0).click();
            return this;
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logFail("Failed to toggle auto-refresh: " + e.getMessage());
            throw new ElementNotFoundException("Failed to toggle auto-refresh", e);
        }
    }

    public DashboardPage enterColumnFilter(int columnIndex, String value) {
        try {
            List<WebElement> filters = driver.findElements(columnFilters);
            if (columnIndex >= filters.size()) {
                logFail("Column index " + columnIndex + " out of bounds");
                throw new ElementNotFoundException("Column index out of bounds: " + columnIndex);
            }
            WebElement filter = filters.get(columnIndex);
            filter.clear();
            filter.sendKeys(value);
            wait.until(driver -> {
                List<WebElement> rows = driver.findElements(tableRows);
                return rows.isEmpty() || !rows.get(0).getText().isEmpty();
            });
            return this;
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logFail("Failed to enter column filter: " + e.getMessage());
            throw new ElementNotFoundException("Failed to enter column filter", e);
        }
    }

    public DashboardPage clearAllFilters() {
        try {
            List<WebElement> filters = driver.findElements(columnFilters);
            filters.forEach(WebElement::clear);
            return this;
        } catch (Exception e) {
            logFail("Failed to clear filters: " + e.getMessage());
            throw new ElementNotFoundException("Failed to clear filters", e);
        }
    }

    /**
     * Adds a comment "comment by AI" on the dashboard page.
     * Assumes presence of a comment input area and a submit button.
     * Throws ElementNotFoundException if elements are not found.
     */
    public DashboardPage addCommentByAI() {
        try {
            WebElement commentBox = wait.until(ExpectedConditions.visibilityOfElementLocated(commentInput));
            commentBox.clear();
            commentBox.sendKeys("comment by AI");
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(commentSubmitButton));
            submitButton.click();
            // Optionally wait for comment to be posted or confirmation
            return this;
        } catch (Exception e) {
            logFail("Failed to add comment by AI: " + e.getMessage());
            throw new ElementNotFoundException("Failed to add comment by AI", e);
        }
    }
}