package com.fleetmgmt.model;

public class Fleet {
    private int fleetId;
    private String fleetName;
    private String companyName;
    private String contactPhone;

    public Fleet() {
    }

    public Fleet(int fleetId, String fleetName, String companyName, String contactPhone) {
        this.fleetId = fleetId;
        this.fleetName = fleetName;
        this.companyName = companyName;
        this.contactPhone = contactPhone;
    }

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
