package com.fleetmgmt.model;

/**
 * Trip entity class representing a trip made by a vehicle and driver.
 */
public class Trip {

    // Private attributes (encapsulation)
    private int tripId;
    private int vehicleId;
    private int driverId;
    private String departureDate;
    private String arrivalDate;
    private double distance;
    private double cost;
    private String status;

    // Default constructor
    public Trip() {
    }

    // Parameterized constructor
    public Trip(int tripId, int vehicleId, int driverId, String departureDate,
                String arrivalDate, double distance, double cost, String status) {
        this.tripId = tripId;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.distance = distance;
        this.cost = cost;
        this.status = status;
    }

    // Getters and Setters
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method for display
    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", vehicleId=" + vehicleId +
                ", driverId=" + driverId +
                ", departure='" + departureDate + '\'' +
                ", arrival='" + arrivalDate + '\'' +
                ", distance=" + distance +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                '}';
    }
}
