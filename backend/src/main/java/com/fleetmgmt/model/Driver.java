package com.fleetmgmt.model;

public class Driver {
    private int driverId;
    private int fleetId;
    private String name;
    private String licenseNumber;
    private String phone;
    private String hireDate;
    private String status;

    public Driver() {
    }

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
