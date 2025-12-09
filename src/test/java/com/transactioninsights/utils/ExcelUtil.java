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

    /**
     * Get a connection to the database.
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Execute a query and return the ResultSet.
     * Caller is responsible for closing the ResultSet and Connection.
     * 
     * @param query SQL query string
     * @return ResultSet of the query
     * @throws SQLException if query execution fails
     */
    public static ResultSet executeQuery(String query) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    /**
     * Execute an update/insert/delete statement.
     * 
     * @param sql SQL statement string
     * @return number of affected rows
     * @throws SQLException if execution fails
     */
    public static int executeUpdate(String sql) throws SQLException {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    /**
     * Close resources safely.
     * 
     * @param conn Connection object
     * @param stmt Statement object
     * @param rs ResultSet object
     */
    public static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException ignored) {
        }
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException ignored) {
        }
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ignored) {
        }
    }
}

{
  "code": "package com.transactioninsights.utils;\n\nimport java.sql.Connection;\nimport java.sql.DriverManager;\nimport java.sql.ResultSet;\nimport java.sql.SQLException;\nimport java.sql.Statement;\n\npublic class DbUtil {\n\n    private static final String DB_URL = \"jdbc:mysql://localhost:3306/your_database\";\n    private static final String DB_USER = \"your_username\";\n    private static final String DB_PASSWORD = \"your_password\";\n\n    /**\n     * Get a connection to the database.\n     * \n     * @return Connection object\n     * @throws SQLException if connection fails\n     */\n    public static Connection getConnection() throws SQLException {\n        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);\n    }\n\n    /**\n     * Execute a query and return the ResultSet.\n     * Caller is responsible for closing the ResultSet and Connection.\n     * \n     * @param query SQL query string\n     * @return ResultSet of the query\n     * @throws SQLException if query execution fails\n     */\n    public static ResultSet executeQuery(String query) throws SQLException {\n        Connection conn = getConnection();\n        Statement stmt = conn.createStatement();\n        return stmt.executeQuery(query);\n    }\n\n    /**\n     * Execute an update/insert/delete statement.\n     * \n     * @param sql SQL statement string\n     * @return number of affected rows\n     * @throws SQLException if execution fails\n     */\n    public static int executeUpdate(String sql) throws SQLException {\n        try (Connection conn = getConnection();\n                Statement stmt = conn.createStatement()) {\n            return stmt.executeUpdate(sql);\n        }\n    }\n\n    /**\n     * Close resources safely.\n     * \n     * @param conn Connection object\n     * @param stmt Statement object\n     * @param rs ResultSet object\n     */\n    public static void closeResources(Connection conn, Statement stmt, ResultSet rs) {\n        try {\n            if (rs != null && !rs.isClosed()) {\n                rs.close();\n            }\n        } catch (SQLException ignored) {\n        }\n        try {\n            if (stmt != null && !stmt.isClosed()) {\n                stmt.close();\n            }\n        } catch (SQLException ignored) {\n        }\n        try {\n            if (conn != null && !conn.isClosed()) {\n                conn.close();\n            }\n        } catch (SQLException ignored) {\n        }\n    }\n}\n",
  "summary": "Added a new DbUtil class in the utils package to provide database utility methods for connection management, query execution, update execution, and resource cleanup.",
  "modified_lines": "entire new file"
}