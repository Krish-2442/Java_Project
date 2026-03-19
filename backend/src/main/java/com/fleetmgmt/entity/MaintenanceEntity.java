package com.fleetmgmt.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Maintenance JPA Entity — maps to the Maintenance table in MySQL.
 * Tracks scheduled and completed maintenance for vehicles.
 */
@Entity
@Table(name = "Maintenance")
public class MaintenanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Maintenance_ID")
    private Integer maintenanceId;

    @Column(name = "Vehicle_ID", nullable = false)
    private Integer vehicleId;

    @Column(name = "Maintenance_Type", nullable = false, length = 50)
    private String maintenanceType;

    @Column(name = "Maintenance_Date", nullable = false)
    private LocalDate maintenanceDate;

    @Column(name = "Cost", nullable = false)
    private Double cost;

    @Column(name = "Status", nullable = false, length = 20)
    private String status = "Scheduled";

    // Constructors
    public MaintenanceEntity() {}

    // Getters and Setters
    public Integer getMaintenanceId() { return maintenanceId; }
    public void setMaintenanceId(Integer maintenanceId) { this.maintenanceId = maintenanceId; }

    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }

    public String getMaintenanceType() { return maintenanceType; }
    public void setMaintenanceType(String maintenanceType) { this.maintenanceType = maintenanceType; }

    public LocalDate getMaintenanceDate() { return maintenanceDate; }
    public void setMaintenanceDate(LocalDate maintenanceDate) { this.maintenanceDate = maintenanceDate; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
