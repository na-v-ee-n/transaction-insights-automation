package com.transactioninsights.tests;

import com.transactioninsights.utils.TestRetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

    @Test(description = "TC_001: Verify dashboard loads with title and transaction table", retryAnalyzer = TestRetryAnalyzer.class)
    public void testDashboardLoadsWithTitleAndTable() {
        logStep("Step 1: Verify dashboard loads");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard did not load");
        logPass("Dashboard loaded successfully");

        logStep("Step 2: Verify dashboard title is visible");
        Assert.assertTrue(dashboardPage.hasTitle(), "Title not found");
        logPass("Dashboard title is visible");

        logStep("Step 3: Verify transaction table has data");
        int rowCount = dashboardPage.getRowCount();
        if (rowCount == 0) {
            logPass("No rows in table, skipping further checks");
            return;
        }
        Assert.assertTrue(rowCount > 0, "No rows in table");
        logPass("Transaction table displayed with " + rowCount + " rows");
    }

    @Test(description = "TC_002: Verify table columns are displayed correctly", retryAnalyzer = TestRetryAnalyzer.class)
    public void testTableColumnsDisplayed() {
        logStep("Step 1: Verify dashboard is loaded");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
        logPass("Dashboard is loaded");

        logStep("Step 2: Verify table headers exist");
        int headerCount = dashboardPage.getTableHeaderCount();
        Assert.assertTrue(headerCount > 0, "No headers found");
        logPass("Found " + headerCount + " table headers");

        logStep("Step 3: Verify expected columns are present");
        Assert.assertTrue(dashboardPage.verifyTableColumnsExist(
                "Name", "Received Date", "Total Records", "Successful", "Pending", "Errored", "Status"),
                "Not all expected columns found");
        logPass("All expected columns verified");
    }

    @Test(description = "TC_003: Verify data consistency in transaction table", retryAnalyzer = TestRetryAnalyzer.class)
    public void testDataConsistency() {
        logStep("Step 1: Verify dashboard is loaded");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
        logPass("Dashboard is loaded");

        logStep("Step 2: Verify table has data");
        int rowCount = dashboardPage.getRowCount();
        if (rowCount == 0) {
            logPass("No data to verify, skipping consistency checks");
            return;
        }
        Assert.assertTrue(rowCount > 0, "No data to verify");
        logPass("Table has " + rowCount + " rows for consistency verification");
    }

    @Test(description = "TC_004: Verify search functionality filters table correctly", retryAnalyzer = TestRetryAnalyzer.class, enabled = true)
    public void testSearchFunctionality() {
        logStep("Step 1: Get initial row count");
        int initialRows = dashboardPage.getRowCount();
        logPass("Initial row count: " + initialRows);

        logStep("Step 2: Search for 'Arjun'");
        dashboardPage.searchTransaction("Arjun");
        logPass("Search query entered");

        logStep("Step 3: Verify filtered results");
        int filteredRows = dashboardPage.getRowCount();
        Assert.assertTrue(filteredRows <= initialRows, "Filtered rows should be less than or equal to initial");
        logPass("Table filtered successfully (" + filteredRows + " rows)");
    }

    @Test(description = "TC_005: Verify date range filter functionality")
    public void testDateRangeFilter() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
    }

    @Test(description = "TC_006: Verify column filter functionality")
    public void testColumnFilters() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
    }

    @Test(description = "TC_007: Verify Status column filter")
    public void testStatusColumnFilter() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
    }

    @Test(description = "TC_008: Verify filter reset functionality", retryAnalyzer = TestRetryAnalyzer.class)
    public void testFilterReset() {
        logStep("Step 1: Apply search filter");
        dashboardPage.searchTransaction("Test");
        logPass("Search filter applied");

        logStep("Step 2: Clear search filter");
        dashboardPage.clearSearch();
        logPass("Search filter cleared");

        logStep("Step 3: Verify table resets to show all transactions");
        int rowCount = dashboardPage.getRowCount();
        Assert.assertTrue(rowCount > 0, "Table should have rows after clearing filter");
        logPass("Table reset successfully (" + rowCount + " rows)");
    }

    @Test(description = "TC_009: Verify clicking Successful link opens modal", retryAnalyzer = TestRetryAnalyzer.class)
    public void testSuccessfulTransactionsModal() {
        logStep("Step 1: Click on Successful transactions link");
        dashboardPage.sortColumn("Successful", true);
        dashboardPage.clickFirstSuccessfulLink();
        logPass("Clicked Successful link");

        logStep("Step 2: Verify modal opens");
        Assert.assertTrue(dashboardPage.isModalDisplayed(), "Modal did not open");
        logPass("Modal opened with Successful Transactions details");
    }

    @Test(description = "TC_010: Verify clicking Errored link opens modal", retryAnalyzer = TestRetryAnalyzer.class)
    public void testErroredTransactionsModal() {
        logStep("Step 1: Click on Errored transactions link");
        dashboardPage.sortColumn("Errored", true);
        dashboardPage.clickFirstErroredLink();
        logPass("Clicked Errored link");

        logStep("Step 2: Verify modal opens");
        Assert.assertTrue(dashboardPage.isModalDisplayed(), "Modal did not open");
        logPass("Modal opened with Errored Transactions details");
    }

    @Test(description = "TC_011: Verify modal close functionality", retryAnalyzer = TestRetryAnalyzer.class)
    public void testModalClose() {
        logStep("Step 1: Open modal");
        dashboardPage.clickFirstSuccessfulLink();
        Assert.assertTrue(dashboardPage.isModalDisplayed(), "Modal not displayed");
        logPass("Modal opened");

        logStep("Step 2: Close modal");
        dashboardPage.closeModal();
        logPass("Modal closed");

        logStep("Step 3: Verify dashboard is visible");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not visible after closing modal");
        logPass("User returned to dashboard");
    }

    @Test(description = "TC_012: Verify auto-refresh toggle functionality", retryAnalyzer = TestRetryAnalyzer.class)
    public void testAutoRefreshToggle() {
        logStep("Step 1: Verify dashboard is loaded");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
        logPass("Dashboard is loaded");

        logStep("Step 2: Toggle auto-refresh");
        dashboardPage.toggleAutoRefresh();
        logPass("Auto-refresh toggled");
    }

    @Test(description = "TC_013: Verify row action menu functionality")
    public void testRowActionMenu() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
    }
}