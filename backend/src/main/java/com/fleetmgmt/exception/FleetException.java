package com.fleetmgmt.exception;

/**
 * Custom exception class for Fleet Management System.
 * Used for invalid operations like duplicate registrations, invalid IDs, etc.
 */
public class FleetException extends Exception {

    // Constructor with message only
    public FleetException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public FleetException(String message, Throwable cause) {
        super(message, cause);
    }
}
