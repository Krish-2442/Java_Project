package com.fleetmgmt.model;

/**
 * Fleet entity class representing a fleet of vehicles.
 * Each fleet belongs to a company and has a unique identifier.
 */
public class Fleet {

    // Private attributes (encapsulation)
    private int fleetId;
    private String fleetName;
    private String companyName;
    private String contactPhone;

    // Default constructor
    public Fleet() {
    }

    // Parameterized constructor
    public Fleet(int fleetId, String fleetName, String companyName, String contactPhone) {
        this.fleetId = fleetId;
        this.fleetName = fleetName;
        this.companyName = companyName;
        this.contactPhone = contactPhone;
    }

    // Getters and Setters
    public int getFleetId() {
        return fleetId;
    }

    public void setFleetId(int fleetId) {
        this.fleetId = fleetId;
    }

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    // toString method for display
    @Override
    public String toString() {
        return "Fleet{" +
                "fleetId=" + fleetId +
                ", fleetName='" + fleetName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                '}';
    }
}
