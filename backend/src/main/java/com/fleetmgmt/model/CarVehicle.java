package com.fleetmgmt.model;

public class CarVehicle extends Vehicle {

    public CarVehicle() {
        super();
    }

    public CarVehicle(int vehicleId, int fleetId, String registrationNumber,
                      String make, String model, int year,
                      String fuelType, String status) {
        super(vehicleId, fleetId, registrationNumber, make, model, year, fuelType, status);
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }

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
