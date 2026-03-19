package com.fleetmgmt.model;

/**
 * CarVehicle — extends abstract Vehicle class.
 * Demonstrates INHERITANCE and METHOD OVERRIDING.
 */
public class CarVehicle extends Vehicle {

    // Default constructor
    public CarVehicle() {
        super();
    }

    // Parameterized constructor
    public CarVehicle(int vehicleId, int fleetId, String registrationNumber,
                      String make, String model, int year,
                      String fuelType, String status) {
        super(vehicleId, fleetId, registrationNumber, make, model, year, fuelType, status);
    }

    // Implementing abstract method (polymorphism)
    @Override
    public String getVehicleType() {
        return "Car";
    }

    // Overriding toString
    @Override
    public String toString() {
        return "CarVehicle{" +
                "vehicleId=" + getVehicleId() +
                ", fleetId=" + getFleetId() +
                ", registration='" + getRegistrationNumber() + '\'' +
                ", make='" + getMake() + '\'' +
                ", model='" + getModel() + '\'' +
                ", year=" + getYear() +
                ", fuelType='" + getFuelType() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", type='Car'" +
                '}';
    }
}
