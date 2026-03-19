package com.fleetmgmt.model;

/**
 * Driver entity class representing a driver assigned to a fleet.
 */
public class Driver {

    // Private attributes (encapsulation)
    private int driverId;
    private int fleetId;
    private String name;
    private String licenseNumber;
    private String phone;
    private String hireDate;
    private String status;

    // Default constructor
    public Driver() {
    }

    // Parameterized constructor
    public Driver(int driverId, int fleetId, String name, String licenseNumber,
                  String phone, String hireDate, String status) {
        this.driverId = driverId;
        this.fleetId = fleetId;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.phone = phone;
        this.hireDate = hireDate;
        this.status = status;
    }

    // Getters and Setters
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getFleetId() {
        return fleetId;
    }

    public void setFleetId(int fleetId) {
        this.fleetId = fleetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
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
        return "Driver{" +
                "driverId=" + driverId +
                ", fleetId=" + fleetId +
                ", name='" + name + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
