package com.fleetmgmt.model;

public abstract class Vehicle {

    private int vehicleId;
    private int fleetId;
    private String registrationNumber;
    private String make;
    private String model;
    private int year;
    private String fuelType;
    private String status;

    public Vehicle() {
    }

    public Vehicle(int vehicleId, int fleetId, String registrationNumber, String make, String model, int year, String fuelType, String status) {
        this.vehicleId = vehicleId;
        this.fleetId = fleetId;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.status = status;
    }

    public abstract String getVehicleType();

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
