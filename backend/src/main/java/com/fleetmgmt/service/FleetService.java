package com.fleetmgmt.service;

import com.fleetmgmt.exception.FleetException;
import com.fleetmgmt.model.Fleet;
import com.fleetmgmt.util.DatabaseConnection;
import com.fleetmgmt.util.FileLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Fleet CRUD operations.
 */
public class FleetService {

    /**
     * Adds a new fleet to the database.
     */
    public void addFleet(Fleet fleet) throws FleetException {
        String sql = "INSERT INTO Fleet (Fleet_Name, Company_Name, Contact_Phone) VALUES (?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fleet.getFleetName());
            stmt.setString(2, fleet.getCompanyName());
            stmt.setString(3, fleet.getContactPhone());
            stmt.executeUpdate();
            stmt.close();
            FileLogger.log("ADD FLEET: " + fleet.getFleetName());
            System.out.println("Fleet added successfully!");
        } catch (SQLException e) {
            FileLogger.logError("ADD FLEET", e.getMessage());
            throw new FleetException("Error adding fleet: " + e.getMessage(), e);
        }
    }

    /**
     * Returns all fleets from the database.
     */
    public List<Fleet> getAllFleets() throws FleetException {
        List<Fleet> fleets = new ArrayList<>();
        String sql = "SELECT * FROM Fleet";
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Fleet fleet = new Fleet(
                        rs.getInt("Fleet_ID"),
                        rs.getString("Fleet_Name"),
                        rs.getString("Company_Name"),
                        rs.getString("Contact_Phone")
                );
                fleets.add(fleet);
            }
            rs.close();
            stmt.close();
            FileLogger.log("VIEW ALL FLEETS: " + fleets.size() + " records found");
        } catch (SQLException e) {
            FileLogger.logError("VIEW ALL FLEETS", e.getMessage());
            throw new FleetException("Error fetching fleets: " + e.getMessage(), e);
        }
        return fleets;
    }

    /**
     * Returns a fleet by its ID.
     */
    public Fleet getFleetById(int fleetId) throws FleetException {
        String sql = "SELECT * FROM Fleet WHERE Fleet_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, fleetId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Fleet fleet = new Fleet(
                        rs.getInt("Fleet_ID"),
                        rs.getString("Fleet_Name"),
                        rs.getString("Company_Name"),
                        rs.getString("Contact_Phone")
                );
                rs.close();
                stmt.close();
                FileLogger.log("VIEW FLEET BY ID: " + fleetId);
                return fleet;
            }
            rs.close();
            stmt.close();
            throw new FleetException("Fleet with ID " + fleetId + " not found.");
        } catch (SQLException e) {
            FileLogger.logError("VIEW FLEET BY ID", e.getMessage());
            throw new FleetException("Error fetching fleet: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing fleet.
     */
    public void updateFleet(Fleet fleet) throws FleetException {
        String sql = "UPDATE Fleet SET Fleet_Name = ?, Company_Name = ?, Contact_Phone = ? WHERE Fleet_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fleet.getFleetName());
            stmt.setString(2, fleet.getCompanyName());
            stmt.setString(3, fleet.getContactPhone());
            stmt.setInt(4, fleet.getFleetId());
            int rows = stmt.executeUpdate();
            stmt.close();
            if (rows == 0) {
                throw new FleetException("Fleet with ID " + fleet.getFleetId() + " not found.");
            }
            FileLogger.log("UPDATE FLEET: ID " + fleet.getFleetId());
            System.out.println("Fleet updated successfully!");
        } catch (SQLException e) {
            FileLogger.logError("UPDATE FLEET", e.getMessage());
            throw new FleetException("Error updating fleet: " + e.getMessage(), e);
        }
    }
}
