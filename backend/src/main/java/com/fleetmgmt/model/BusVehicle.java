package com.fleetmgmt.model;
public class BusVehicle extends Vehicle {
    
    private int seatingCapacity;

    public BusVehicle() {
        super();
    }

    public BusVehicle(int vehicleId, int fleetId, String registrationNumber,
                      String make, String model, int year,
                      String fuelType, String status, int seatingCapacity) {
        super(vehicleId, fleetId, registrationNumber, make, model, year, fuelType, status);
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public String getVehicleType() {
        return "Bus";
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

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
