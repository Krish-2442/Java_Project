package com.fleetmgmt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing the MySQL database connection using JDBC.
 * Uses a simple singleton pattern — one shared connection.
 *
 * IMPORTANT: Update the URL, USERNAME, and PASSWORD below
 *            to match your local MySQL setup.
 */
public class DatabaseConnection {

    // ======= UPDATE THESE VALUES TO MATCH YOUR MYSQL SETUP =======
    private static final String URL = "jdbc:mysql://localhost:3306/fleet_management_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Krish#244";
    // ==============================================================

    private static Connection connection = null;

    // Private constructor — prevents instantiation
    private DatabaseConnection() {
    }

    /**
     * Returns a single shared Connection to the MySQL database.
     * Creates the connection on first call, reuses on subsequent calls.
     */
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("[DB] Connected to database successfully.");
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. "
                    + "Make sure mysql-connector-java is in your classpath.", e);
        }
        return connection;
    }

    /**
     * Closes the shared database connection.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Error closing connection: " + e.getMessage());
        }
    }
}