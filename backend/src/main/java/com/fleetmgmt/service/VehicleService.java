package com.fleetmgmt.service;

import com.fleetmgmt.exception.FleetException;
import com.fleetmgmt.model.BusVehicle;
import com.fleetmgmt.model.CarVehicle;
import com.fleetmgmt.model.Vehicle;
import com.fleetmgmt.util.DatabaseConnection;
import com.fleetmgmt.util.FileLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Vehicle CRUD operations.
 * Demonstrates POLYMORPHISM: returns List<Vehicle> containing CarVehicle and BusVehicle.
 */
public class VehicleService {

    /**
     * Registers a new vehicle in the database.
     */
    public void registerVehicle(Vehicle vehicle) throws FleetException {
        String sql = "INSERT INTO Vehicle (Fleet_ID, Registration_Number, Make, Model, Year, "
                + "Fuel_Type, Vehicle_Type, Seating_Capacity, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vehicle.getFleetId());
            stmt.setString(2, vehicle.getRegistrationNumber());
            stmt.setString(3, vehicle.getMake());
            stmt.setString(4, vehicle.getModel());
            stmt.setInt(5, vehicle.getYear());
            stmt.setString(6, vehicle.getFuelType());
            stmt.setString(7, vehicle.getVehicleType());

            // Set seating capacity based on vehicle type
            if (vehicle instanceof BusVehicle) {
                stmt.setInt(8, ((BusVehicle) vehicle).getSeatingCapacity());
            } else {
                stmt.setInt(8, 5); // default for cars
            }

            stmt.setString(9, vehicle.getStatus());
            stmt.executeUpdate();
            stmt.close();
            FileLogger.log("REGISTER VEHICLE: " + vehicle.getRegistrationNumber()
                    + " [" + vehicle.getVehicleType() + "]");
            System.out.println("Vehicle registered successfully!");
        } catch (SQLException e) {
            FileLogger.logError("REGISTER VEHICLE", e.getMessage());
            throw new FleetException("Error registering vehicle: " + e.getMessage(), e);
        }
    }

    /**
     * Returns all vehicles belonging to a fleet.
     * Demonstrates POLYMORPHISM — List<Vehicle> holding CarVehicle and BusVehicle objects.
     */
    public List<Vehicle> getVehiclesByFleet(int fleetId) throws FleetException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicle WHERE Fleet_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, fleetId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = buildVehicleFromResultSet(rs);
                vehicles.add(vehicle);
            }
            rs.close();
            stmt.close();
            FileLogger.log("VIEW VEHICLES BY FLEET: Fleet " + fleetId
                    + " — " + vehicles.size() + " vehicles");
        } catch (SQLException e) {
            FileLogger.logError("VIEW VEHICLES BY FLEET", e.getMessage());
            throw new FleetException("Error fetching vehicles: " + e.getMessage(), e);
        }
        return vehicles;
    }

    /**
     * Returns all vehicles in the database (for reports).
     */
    public List<Vehicle> getAllVehicles() throws FleetException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicle";
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Vehicle vehicle = buildVehicleFromResultSet(rs);
                vehicles.add(vehicle);
            }
            rs.close();
            stmt.close();
            FileLogger.log("VIEW ALL VEHICLES: " + vehicles.size() + " records");
        } catch (SQLException e) {
            FileLogger.logError("VIEW ALL VEHICLES", e.getMessage());
            throw new FleetException("Error fetching vehicles: " + e.getMessage(), e);
        }
        return vehicles;
    }

    /**
     * Updates the status of a vehicle (e.g., Available, In Service, Under Maintenance).
     */
    public void updateVehicleStatus(int vehicleId, String newStatus) throws FleetException {
        String sql = "UPDATE Vehicle SET Status = ? WHERE Vehicle_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, vehicleId);
            int rows = stmt.executeUpdate();
            stmt.close();
            if (rows == 0) {
                throw new FleetException("Vehicle with ID " + vehicleId + " not found.");
            }
            FileLogger.log("UPDATE VEHICLE STATUS: ID " + vehicleId + " => " + newStatus);
            System.out.println("Vehicle status updated successfully!");
        } catch (SQLException e) {
            FileLogger.logError("UPDATE VEHICLE STATUS", e.getMessage());
            throw new FleetException("Error updating vehicle status: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes a vehicle from the database.
     */
    public void deleteVehicle(int vehicleId) throws FleetException {
        String sql = "DELETE FROM Vehicle WHERE Vehicle_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vehicleId);
            int rows = stmt.executeUpdate();
            stmt.close();
            if (rows == 0) {
                throw new FleetException("Vehicle with ID " + vehicleId + " not found.");
            }
            FileLogger.log("DELETE VEHICLE: ID " + vehicleId);
            System.out.println("Vehicle deleted successfully!");
        } catch (SQLException e) {
            FileLogger.logError("DELETE VEHICLE", e.getMessage());
            throw new FleetException("Error deleting vehicle: " + e.getMessage(), e);
        }
    }

    /**
     * Helper: builds a CarVehicle or BusVehicle from a ResultSet row.
     */
    private Vehicle buildVehicleFromResultSet(ResultSet rs) throws SQLException {
        String type = rs.getString("Vehicle_Type");
        if ("Bus".equalsIgnoreCase(type)) {
            return new BusVehicle(
                    rs.getInt("Vehicle_ID"),
                    rs.getInt("Fleet_ID"),
                    rs.getString("Registration_Number"),
                    rs.getString("Make"),
                    rs.getString("Model"),
                    rs.getInt("Year"),
                    rs.getString("Fuel_Type"),
                    rs.getString("Status"),
                    rs.getInt("Seating_Capacity")
            );
        } else {
            return new CarVehicle(
                    rs.getInt("Vehicle_ID"),
                    rs.getInt("Fleet_ID"),
                    rs.getString("Registration_Number"),
                    rs.getString("Make"),
                    rs.getString("Model"),
                    rs.getInt("Year"),
                    rs.getString("Fuel_Type"),
                    rs.getString("Status")
            );
        }
    }
}
