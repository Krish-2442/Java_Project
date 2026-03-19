package com.fleetmgmt.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Driver JPA Entity — maps to the Driver table in MySQL.
 * Each driver is assigned to a fleet and can be assigned to trips.
 */
@Entity
@Table(name = "Driver")
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Driver_ID")
    private Integer driverId;

    @Column(name = "Fleet_ID", nullable = false)
    private Integer fleetId;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Column(name = "License_Number", nullable = false, unique = true, length = 30)
    private String licenseNumber;

    @Column(name = "Phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "Hire_Date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "Status", nullable = false, length = 20)
    private String status = "Available";

    // Constructors
    public DriverEntity() {}

    // Getters and Setters
    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }

    public Integer getFleetId() { return fleetId; }
    public void setFleetId(Integer fleetId) { this.fleetId = fleetId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
