package com.fleetmgmt.model;

/**
 * BusVehicle — extends abstract Vehicle class.
 * Demonstrates INHERITANCE, METHOD OVERRIDING, and additional attributes.
 */
public class BusVehicle extends Vehicle {

    // Additional attribute specific to Bus
    private int seatingCapacity;

    // Default constructor
    public BusVehicle() {
        super();
    }

    // Parameterized constructor
    public BusVehicle(int vehicleId, int fleetId, String registrationNumber,
                      String make, String model, int year,
                      String fuelType, String status, int seatingCapacity) {
        super(vehicleId, fleetId, registrationNumber, make, model, year, fuelType, status);
        this.seatingCapacity = seatingCapacity;
    }

    // Implementing abstract method (polymorphism)
    @Override
    public String getVehicleType() {
        return "Bus";
    }

    // Getter and Setter for seatingCapacity
    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    // Overriding toString
    @Override
    public String toString() {
        return "BusVehicle{" +
                "vehicleId=" + getVehicleId() +
                ", fleetId=" + getFleetId() +
                ", registration='" + getRegistrationNumber() + '\'' +
                ", make='" + getMake() + '\'' +
                ", model='" + getModel() + '\'' +
                ", year=" + getYear() +
                ", fuelType='" + getFuelType() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", type='Bus'" +
                ", seatingCapacity=" + seatingCapacity +
                '}';
    }
}
