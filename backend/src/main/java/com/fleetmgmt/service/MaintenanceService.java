package com.fleetmgmt.service;

import com.fleetmgmt.exception.FleetException;
import com.fleetmgmt.util.DatabaseConnection;
import com.fleetmgmt.util.FileLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Maintenance operations.
 * Note: Maintenance does not have a separate model class — results are
 * displayed directly using formatted strings to keep the project simple.
 */
public class MaintenanceService {

    /**
     * Schedules a new maintenance entry for a vehicle.
     */
    public void scheduleMaintenance(int vehicleId, String maintenanceType,
                                    String date, double cost) throws FleetException {
        String sql = "INSERT INTO Maintenance (Vehicle_ID, Maintenance_Type, Maintenance_Date, "
                + "Cost, Status) VALUES (?, ?, ?, ?, 'Scheduled')";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vehicleId);
            stmt.setString(2, maintenanceType);
            stmt.setString(3, date);
            stmt.setDouble(4, cost);
            stmt.executeUpdate();
            stmt.close();
            FileLogger.log("SCHEDULE MAINTENANCE: Vehicle " + vehicleId
                    + " — " + maintenanceType);
            System.out.println("Maintenance scheduled successfully!");
        } catch (SQLException e) {
            FileLogger.logError("SCHEDULE MAINTENANCE", e.getMessage());
            throw new FleetException("Error scheduling maintenance: " + e.getMessage(), e);
        }
    }

    /**
     * Returns all maintenance records for a specific vehicle.
     * Each record is returned as a formatted string.
     */
    public List<String> getMaintenanceByVehicle(int vehicleId) throws FleetException {
        List<String> records = new ArrayList<>();
        String sql = "SELECT * FROM Maintenance WHERE Vehicle_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String record = String.format(
                        "Maintenance ID: %d | Type: %s | Date: %s | Cost: Rs. %.2f | Status: %s",
                        rs.getInt("Maintenance_ID"),
                        rs.getString("Maintenance_Type"),
                        rs.getString("Maintenance_Date"),
                        rs.getDouble("Cost"),
                        rs.getString("Status")
                );
                records.add(record);
            }
            rs.close();
            stmt.close();
            FileLogger.log("VIEW MAINTENANCE: Vehicle " + vehicleId
                    + " — " + records.size() + " records");
        } catch (SQLException e) {
            FileLogger.logError("VIEW MAINTENANCE", e.getMessage());
            throw new FleetException("Error fetching maintenance records: " + e.getMessage(), e);
        }
        return records;
    }

    /**
     * Marks a maintenance record as Completed.
     */
    public void completeMaintenance(int maintenanceId) throws FleetException {
        String sql = "UPDATE Maintenance SET Status = 'Completed' WHERE Maintenance_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maintenanceId);
            int rows = stmt.executeUpdate();
            stmt.close();
            if (rows == 0) {
                throw new FleetException("Maintenance record with ID "
                        + maintenanceId + " not found.");
            }
            FileLogger.log("COMPLETE MAINTENANCE: ID " + maintenanceId);
            System.out.println("Maintenance marked as completed!");
        } catch (SQLException e) {
            FileLogger.logError("COMPLETE MAINTENANCE", e.getMessage());
            throw new FleetException("Error completing maintenance: " + e.getMessage(), e);
        }
    }
}
