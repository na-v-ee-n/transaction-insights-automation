package com.transactioninsights.tests;

import com.transactioninsights.config.TestConfig;
import com.transactioninsights.pages.DashboardPage;
import com.transactioninsights.utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {

    // Comment by AGent

    protected WebDriver driver;
    protected ExtentTest test;
    protected DashboardPage dashboardPage;

    @BeforeSuite
    public void setUpSuite() {
        ExtentReportManager.getInstance();
    }

    @BeforeMethod
    public void setUp(Method method) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestConfig.getImplicitWait()));
        driver.get(TestConfig.getAppUrl());

        Test testAnnotation = method.getAnnotation(Test.class);
        String testName = method.getName().replaceAll("([A-Z])", " $1").trim();
        test = ExtentReportManager.createTest(testName, testAnnotation.description());
        test.assignCategory("Dashboard Testing");
        dashboardPage = new DashboardPage(driver, test);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (test != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                captureScreenshot(result.getName());
                test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.log(Status.PASS, "Test Passed");
            } else {
                test.log(Status.SKIP, "Test Skipped");
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        ExtentReportManager.flush();
    }

    private void captureScreenshot(String testName) {
        if (!TestConfig.isScreenshotOnFailureEnabled()) {
            return;
        }

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);

            // Embed screenshot in ExtentReport
            test.addScreenCaptureFromBase64String(base64Screenshot, "Failure Screenshot - " + testName);
            test.log(Status.INFO, "Screenshot captured for failed test");

        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }

    protected void logStep(String step) {
        test.log(Status.INFO, "<b>" + step + "</b>");
    }

    protected void logPass(String message) {
        test.log(Status.PASS, "✓ " + message);
    }
}