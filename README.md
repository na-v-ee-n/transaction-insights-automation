# Transaction Insights Automation

## Overview
This project is a robust test automation framework designed for the **Transaction Insights** application. It utilizes **Selenium WebDriver** for UI interaction and **TestNG** for test management, providing comprehensive reporting via **ExtentReports**.

The framework is built to ensure the reliability and stability of the Transaction Insights dashboard and related features.

## Tech Stack
-   **Language**: Java 17
-   **Build Tool**: Maven
-   **Web Driver**: Selenium WebDriver (4.16.1)
-   **Test Framework**: TestNG (7.9.0)
-   **Reporting**: ExtentReports (5.1.1)
-   **Utilities**: Apache POI (for data handling), SLF4J (logging)

## Prerequisites
Before running the tests, ensure you have the following installed:
1.  **Java Development Kit (JDK) 17** or higher.
    -   Verify with: `java -version`
2.  **Maven 3.x** or higher.
    -   Verify with: `mvn -version`
3.  **Google Chrome Browser** (latest version recommended).
4.  **IDE** (Optional but recommended): IntelliJ IDEA or Eclipse.

## Installation
1.  **Clone the repository**:
    ```bash
    git clone <repository-url>
    cd transaction-insights-automation
    ```

2.  **Install dependencies**:
    ```bash
    mvn clean install -DskipTests
    ```

## Running Tests

### Option 1: Via Command Line (Maven)
You can execute the tests using the custom `TestRunner` class which configures the suite programmatically.

```bash
mvn clean compile exec:java
```
*Note: This uses the `exec-maven-plugin` configured in `pom.xml` to run `com.transactioninsights.runner.TestRunner`.*

Alternatively, to run via the standard Surefire plugin (using `testng.xml`):
```bash
mvn test
```

### Option 2: Via IDE (IntelliJ / Eclipse)
1.  **Run TestRunner**:
    -   Navigate to `src/test/java/com/transactioninsights/runner/TestRunner.java`.
    -   Right-click and select **Run 'TestRunner.main()'**.

2.  **Run TestNG XML**:
    -   Navigate to `src/test/resources/testng.xml`.
    -   Right-click and select **Run '...testng.xml'**.

## Test Reports
After test execution, a detailed HTML report is generated.

-   **Location**: `test-output/extent-report.html`
-   **Features**:
    -   Dashboard view of pass/fail status.
    -   Detailed steps for each test case.
    -   **Screenshots** are automatically captured and embedded for failed tests.
    -   Execution logs.

## Project Structure
```
transaction-insights-automation/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/transactioninsights/
│       │       ├── config/         # Configuration readers (Config properties)
│       │       ├── pages/          # Page Object Model (POM) classes
│       │       ├── runner/         # Test entry point (TestRunner.java)
│       │       ├── tests/          # Test classes (BaseTest, DashboardTest)
│       │       └── utils/          # Utilities (ExtentReportManager, etc.)
│       └── resources/
│           ├── config.properties   # Global test configuration
│           └── testng.xml          # TestNG suite configuration
├── pom.xml                         # Maven dependencies and build config
└── README.md                       # Project documentation
```

## Configuration
Global settings can be modified in `src/test/resources/config.properties`:
-   `app.url`: The base URL of the application under test.
-   `implicit.wait`: Global wait timeout in seconds.
-   `screenshot.on.failure`: Set to `true` to enable screenshots.
