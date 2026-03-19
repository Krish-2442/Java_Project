package com.fleetmgmt.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for logging operations to a file (logs.txt).
 * Demonstrates FILE HANDLING in Java.
 * Each log entry includes a timestamp and the operation description.
 */
public class FileLogger {

    private static final String LOG_FILE = "logs.txt";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Writes a log entry to logs.txt with the current timestamp.
     *
     * @param operation Description of the operation being logged
     */
    public static void log(String operation) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            writer.println("[" + timestamp + "] " + operation);
        } catch (IOException e) {
            System.err.println("[Logger] Failed to write log: " + e.getMessage());
        }
    }

    /**
     * Writes an error log entry to logs.txt.
     *
     * @param operation Description of the operation
     * @param error     The error message
     */
    public static void logError(String operation, String error) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            writer.println("[" + timestamp + "] ERROR - " + operation + ": " + error);
        } catch (IOException e) {
            System.err.println("[Logger] Failed to write error log: " + e.getMessage());
        }
    }
}
