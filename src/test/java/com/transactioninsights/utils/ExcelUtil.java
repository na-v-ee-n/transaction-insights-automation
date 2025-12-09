package com.transactioninsights.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    private Connection connection;

    public DbUtil() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }

    public int executeUpdate(String query) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute update: " + query, e);
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Log and ignore
            }
        }
    }
}

{
  "code": "package com.transactioninsights.utils;\n\nimport java.sql.Connection;\nimport java.sql.DriverManager;\nimport java.sql.ResultSet;\nimport java.sql.SQLException;\nimport java.sql.Statement;\n\npublic class DbUtil {\n\n    private static final String DB_URL = \"jdbc:mysql://localhost:3306/your_database\";\n    private static final String DB_USER = \"your_username\";\n    private static final String DB_PASSWORD = \"your_password\";\n\n    private Connection connection;\n\n    public DbUtil() {\n        try {\n            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);\n        } catch (SQLException e) {\n            throw new RuntimeException(\"Failed to connect to the database\", e);\n        }\n    }\n\n    public ResultSet executeQuery(String query) {\n        try {\n            Statement stmt = connection.createStatement();\n            return stmt.executeQuery(query);\n        } catch (SQLException e) {\n            throw new RuntimeException(\"Failed to execute query: \" + query, e);\n        }\n    }\n\n    public int executeUpdate(String query) {\n        try {\n            Statement stmt = connection.createStatement();\n            return stmt.executeUpdate(query);\n        } catch (SQLException e) {\n            throw new RuntimeException(\"Failed to execute update: \" + query, e);\n        }\n    }\n\n    public void close() {\n        if (connection != null) {\n            try {\n                connection.close();\n            } catch (SQLException e) {\n                // Log and ignore\n            }\n        }\n    }\n}\n",
  "summary": "Added a new DbUtil class in the utils package to handle database connections and queries. It includes methods to execute queries and updates, and manages connection lifecycle with proper exception handling.",
  "modified_lines": "entire new file"
}