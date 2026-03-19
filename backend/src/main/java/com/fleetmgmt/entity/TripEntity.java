package com.fleetmgmt.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Trip JPA Entity — maps to the Trip table in MySQL.
 * Links a vehicle and driver for a specific journey.
 * Cost is auto-calculated at ₹12/km.
 */
@Entity
@Table(name = "Trip")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Trip_ID")
    private Integer tripId;

    @Column(name = "Vehicle_ID", nullable = false)
    private Integer vehicleId;

    @Column(name = "Driver_ID", nullable = false)
    private Integer driverId;

    @Column(name = "Departure_Date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "Arrival_Date")
    private LocalDate arrivalDate;

    @Column(name = "Distance", nullable = false)
    private Double distance;

    @Column(name = "Cost", nullable = false)
    private Double cost;

    @Column(name = "Status", nullable = false, length = 20)
    private String status = "Scheduled";

    // Constructors
    public TripEntity() {}

    // Getters and Setters
    public Integer getTripId() { return tripId; }
    public void setTripId(Integer tripId) { this.tripId = tripId; }

    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }

    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }

    public LocalDate getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDate departureDate) { this.departureDate = departureDate; }

    public LocalDate getArrivalDate() { return arrivalDate; }
    public void setArrivalDate(LocalDate arrivalDate) { this.arrivalDate = arrivalDate; }

    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
