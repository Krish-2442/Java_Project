package com.fleetmgmt.exception;
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
