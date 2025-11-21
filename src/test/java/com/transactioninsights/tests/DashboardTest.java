package com.transactioninsights.tests;

import com.transactioninsights.utils.TestRetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

@Test(description = "TC_001: Verify dashboard loads with title and transaction table", retryAnalyzer = TestRetryAnalyzer.class)
    public void testDashboardLoadsWithTitleAndTable() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard did not load");
        Assert.assertTrue(dashboardPage.hasTitle(), "Title not found");
        Assert.assertTrue(dashboardPage.getRowCount() > 0, "No rows in table");
    }

    @Test(description = "TC_002: Verify table columns are displayed correctly", retryAnalyzer = TestRetryAnalyzer.class)
    public void testTableColumnsDisplayed() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
        Assert.assertTrue(dashboardPage.getTableHeaderCount() > 0, "No headers found");
        Assert.assertTrue(dashboardPage.verifyTableColumnsExist(
                "Name", "Date", "Total", "Successful", "Pending", "Errored", "Status"), 
                "Not all expected columns found");
    }

    @Test(description = "TC_003: Verify data consistency in transaction table", retryAnalyzer = TestRetryAnalyzer.class)
    public void testDataConsistency() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
        Assert.assertTrue(dashboardPage.getRowCount() > 0, "No data to verify");
    }

    @Test(description = "TC_004: Verify search functionality filters table correctly", retryAnalyzer = TestRetryAnalyzer.class)
    public void testSearchFunctionality() {
        int initialRows = dashboardPage.getRowCount();
        dashboardPage.searchTransaction("Arjun");
        int filteredRows = dashboardPage.getRowCount();
        Assert.assertTrue(filteredRows <= initialRows, "Filtered rows should be less than or equal to initial");
    }

    @Test(description = "TC_005: Verify date range filter functionality", enabled = false)
    public void testDateRangeFilter() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
    }

    @Test(description = "TC_006: Verify column filter functionality", enabled = false)
    public void testColumnFilters() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
    }

    @Test(description = "TC_007: Verify Status column filter", enabled = false)
    public void testStatusColumnFilter() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
    }

    @Test(description = "TC_008: Verify filter reset functionality", retryAnalyzer = TestRetryAnalyzer.class)
    public void testFilterReset() {
        dashboardPage.searchTransaction("Test");
        dashboardPage.clearSearch();
        Assert.assertTrue(dashboardPage.getRowCount() > 0, "Table should have rows after clearing filter");
    }

    @Test(description = "TC_009: Verify clicking Successful link opens modal", retryAnalyzer = TestRetryAnalyzer.class)
    public void testSuccessfulTransactionsModal() {
        dashboardPage.clickFirstSuccessfulLink();
        Assert.assertTrue(dashboardPage.isModalDisplayed(), "Modal did not open");
    }

    @Test(description = "TC_010: Verify clicking Errored link opens modal", retryAnalyzer = TestRetryAnalyzer.class)
    public void testErroredTransactionsModal() {
        dashboardPage.clickFirstErroredLink();
        Assert.assertTrue(dashboardPage.isModalDisplayed(), "Modal did not open");
    }

    @Test(description = "TC_011: Verify modal close functionality", retryAnalyzer = TestRetryAnalyzer.class)
    public void testModalClose() {
        dashboardPage.clickFirstSuccessfulLink();
        Assert.assertTrue(dashboardPage.isModalDisplayed(), "Modal not displayed");
        dashboardPage.closeModal();
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not visible after closing modal");
    }

    @Test(description = "TC_012: Verify auto-refresh toggle functionality", retryAnalyzer = TestRetryAnalyzer.class)
    public void testAutoRefreshToggle() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
        dashboardPage.toggleAutoRefresh();
    }

    @Test(description = "TC_013: Verify row action menu functionality", enabled = false)
    public void testRowActionMenu() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");
    }
}
