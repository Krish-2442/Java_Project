package com.fleetmgmt.entity;

import jakarta.persistence.*;

/**
 * Vehicle JPA Entity — maps to the Vehicle table in MySQL.
 * Supports Car and Bus types via the vehicleType field.
 * Linked to Fleet via ManyToOne relationship.
 */
@Entity
@Table(name = "Vehicle")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Vehicle_ID")
    private Integer vehicleId;

    @Column(name = "Fleet_ID", nullable = false)
    private Integer fleetId;

    @Column(name = "Registration_Number", nullable = false, unique = true, length = 20)
    private String registrationNumber;

    @Column(name = "Make", nullable = false, length = 50)
    private String make;

    @Column(name = "Model", nullable = false, length = 50)
    private String model;

    @Column(name = "Year", nullable = false)
    private Integer year;

    @Column(name = "Fuel_Type", nullable = false, length = 20)
    private String fuelType;

    @Column(name = "Vehicle_Type", nullable = false, length = 10)
    private String vehicleType = "Car";

    @Column(name = "Seating_Capacity")
    private Integer seatingCapacity = 5;

    @Column(name = "Status", nullable = false, length = 20)
    private String status = "Available";

    // Constructors
    public VehicleEntity() {}

    // Getters and Setters
    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }

    public Integer getFleetId() { return fleetId; }
    public void setFleetId(Integer fleetId) { this.fleetId = fleetId; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public Integer getSeatingCapacity() { return seatingCapacity; }
    public void setSeatingCapacity(Integer seatingCapacity) { this.seatingCapacity = seatingCapacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
