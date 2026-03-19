package com.fleetmgmt.entity;

import jakarta.persistence.*;

/**
 * Fleet JPA Entity — maps to the Fleet table in MySQL.
 * Each fleet belongs to a company and manages vehicles and drivers.
 */
@Entity
@Table(name = "Fleet")
public class FleetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Fleet_ID")
    private Integer fleetId;

    @Column(name = "Fleet_Name", nullable = false, length = 100)
    private String fleetName;

    @Column(name = "Company_Name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "Contact_Phone", nullable = false, length = 15)
    private String contactPhone;

    // Constructors
    public FleetEntity() {}

    public FleetEntity(String fleetName, String companyName, String contactPhone) {
        this.fleetName = fleetName;
        this.companyName = companyName;
        this.contactPhone = contactPhone;
    }

    // Getters and Setters
    public Integer getFleetId() { return fleetId; }
    public void setFleetId(Integer fleetId) { this.fleetId = fleetId; }

    public String getFleetName() { return fleetName; }
    public void setFleetName(String fleetName) { this.fleetName = fleetName; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
}
