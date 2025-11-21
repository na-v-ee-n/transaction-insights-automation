package com.transactioninsights.runner;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        // Create TestNG instance
        TestNG testNG = new TestNG();

        // Create XML Suite
        XmlSuite suite = new XmlSuite();
        suite.setName("Transaction Insights Automation Suite");
        suite.setParallel(XmlSuite.ParallelMode.NONE);
        suite.setVerbose(2);

        // Create XML Test
        XmlTest test = new XmlTest(suite);
        test.setName("Dashboard Tests");

        // Add test classes
        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass("com.transactioninsights.tests.DashboardTest"));
        test.setXmlClasses(classes);

        // Add suite to TestNG
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        testNG.setXmlSuites(suites);

        // Set output directory
        testNG.setOutputDirectory("test-output");

        // Run tests
        System.out.println("========================================");
        System.out.println("Starting Transaction Insights Test Suite");
        System.out.println("========================================");

        testNG.run();

        System.out.println("\n========================================");
        System.out.println("Test Execution Completed");
        System.out.println("Check reports at: test-output/extent-report.html");
        System.out.println("========================================");
    }
}
