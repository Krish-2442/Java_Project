package com.fleetmgmt.service;

import com.fleetmgmt.exception.FleetException;
import com.fleetmgmt.model.Driver;
import com.fleetmgmt.util.DatabaseConnection;
import com.fleetmgmt.util.FileLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Driver CRUD operations.
 */
public class DriverService {

    /**
     * Hires (adds) a new driver.
     */
    public void hireDriver(Driver driver) throws FleetException {
        String sql = "INSERT INTO Driver (Fleet_ID, Name, License_Number, Phone, Hire_Date, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, driver.getFleetId());
            stmt.setString(2, driver.getName());
            stmt.setString(3, driver.getLicenseNumber());
            stmt.setString(4, driver.getPhone());
            stmt.setString(5, driver.getHireDate());
            stmt.setString(6, driver.getStatus());
            stmt.executeUpdate();
            stmt.close();
            FileLogger.log("HIRE DRIVER: " + driver.getName());
            System.out.println("Driver hired successfully!");
        } catch (SQLException e) {
            FileLogger.logError("HIRE DRIVER", e.getMessage());
            throw new FleetException("Error hiring driver: " + e.getMessage(), e);
        }
    }

    /**
     * Returns all drivers from the database.
     */
    public List<Driver> getAllDrivers() throws FleetException {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM Driver";
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                drivers.add(buildDriverFromResultSet(rs));
            }
            rs.close();
            stmt.close();
            FileLogger.log("VIEW ALL DRIVERS: " + drivers.size() + " records");
        } catch (SQLException e) {
            FileLogger.logError("VIEW ALL DRIVERS", e.getMessage());
            throw new FleetException("Error fetching drivers: " + e.getMessage(), e);
        }
        return drivers;
    }

    /**
     * Returns all drivers belonging to a specific fleet.
     */
    public List<Driver> getDriversByFleet(int fleetId) throws FleetException {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM Driver WHERE Fleet_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, fleetId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                drivers.add(buildDriverFromResultSet(rs));
            }
            rs.close();
            stmt.close();
            FileLogger.log("VIEW DRIVERS BY FLEET: Fleet " + fleetId
                    + " — " + drivers.size() + " drivers");
        } catch (SQLException e) {
            FileLogger.logError("VIEW DRIVERS BY FLEET", e.getMessage());
            throw new FleetException("Error fetching drivers: " + e.getMessage(), e);
        }
        return drivers;
    }

    /**
     * Updates driver details (name, phone, status).
     */
    public void updateDriver(Driver driver) throws FleetException {
        String sql = "UPDATE Driver SET Name = ?, Phone = ?, Status = ? WHERE Driver_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getPhone());
            stmt.setString(3, driver.getStatus());
            stmt.setInt(4, driver.getDriverId());
            int rows = stmt.executeUpdate();
            stmt.close();
            if (rows == 0) {
                throw new FleetException("Driver with ID " + driver.getDriverId() + " not found.");
            }
            FileLogger.log("UPDATE DRIVER: ID " + driver.getDriverId());
            System.out.println("Driver updated successfully!");
        } catch (SQLException e) {
            FileLogger.logError("UPDATE DRIVER", e.getMessage());
            throw new FleetException("Error updating driver: " + e.getMessage(), e);
        }
    }

    /**
     * Helper: builds a Driver from a ResultSet row.
     */
    private Driver buildDriverFromResultSet(ResultSet rs) throws SQLException {
        return new Driver(
                rs.getInt("Driver_ID"),
                rs.getInt("Fleet_ID"),
                rs.getString("Name"),
                rs.getString("License_Number"),
                rs.getString("Phone"),
                rs.getString("Hire_Date"),
                rs.getString("Status")
        );
    }
}
