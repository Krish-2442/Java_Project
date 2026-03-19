package com.fleetmgmt.model;

/**
 * Abstract Vehicle class — base class for all vehicle types.
 * Demonstrates INHERITANCE: CarVehicle and BusVehicle extend this class.
 */
public abstract class Vehicle {

    // Private attributes (encapsulation)
    private int vehicleId;
    private int fleetId;
    private String registrationNumber;
    private String make;
    private String model;
    private int year;
    private String fuelType;
    private String status;

    // Default constructor
    public Vehicle() {
    }

    // Parameterized constructor
    public Vehicle(int vehicleId, int fleetId, String registrationNumber,
                   String make, String model, int year,
                   String fuelType, String status) {
        this.vehicleId = vehicleId;
        this.fleetId = fleetId;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.status = status;
    }

    // Abstract method — subclasses must implement this (polymorphism)
    public abstract String getVehicleType();

    // Getters and Setters
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getFleetId() {
        return fleetId;
    }

    public void setFleetId(int fleetId) {
        this.fleetId = fleetId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString — overridden in subclasses
    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", fleetId=" + fleetId +
                ", registration='" + registrationNumber + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", fuelType='" + fuelType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
