package com.fleetmgmt.service;

import com.fleetmgmt.exception.FleetException;
import com.fleetmgmt.model.Trip;
import com.fleetmgmt.util.DatabaseConnection;
import com.fleetmgmt.util.FileLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Trip CRUD operations.
 */
public class TripService {

    /**
     * Creates a new trip.
     */
    public void createTrip(Trip trip) throws FleetException {
        String sql = "INSERT INTO Trip (Vehicle_ID, Driver_ID, Departure_Date, Arrival_Date, "
                + "Distance, Cost, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, trip.getVehicleId());
            stmt.setInt(2, trip.getDriverId());
            stmt.setString(3, trip.getDepartureDate());

            // Arrival date can be null for scheduled/in-progress trips
            if (trip.getArrivalDate() != null && !trip.getArrivalDate().isEmpty()) {
                stmt.setString(4, trip.getArrivalDate());
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setDouble(5, trip.getDistance());
            stmt.setDouble(6, trip.getCost());
            stmt.setString(7, trip.getStatus());
            stmt.executeUpdate();
            stmt.close();
            FileLogger.log("CREATE TRIP: Vehicle " + trip.getVehicleId()
                    + ", Driver " + trip.getDriverId());
            System.out.println("Trip created successfully!");
        } catch (SQLException e) {
            FileLogger.logError("CREATE TRIP", e.getMessage());
            throw new FleetException("Error creating trip: " + e.getMessage(), e);
        }
    }

    /**
     * Returns all trips from the database.
     */
    public List<Trip> getAllTrips() throws FleetException {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM Trip";
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                trips.add(buildTripFromResultSet(rs));
            }
            rs.close();
            stmt.close();
            FileLogger.log("VIEW ALL TRIPS: " + trips.size() + " records");
        } catch (SQLException e) {
            FileLogger.logError("VIEW ALL TRIPS", e.getMessage());
            throw new FleetException("Error fetching trips: " + e.getMessage(), e);
        }
        return trips;
    }

    /**
     * Calculates estimated trip cost based on distance.
     * Simple formula: cost = distance * rate per km
     */
    public double calculateTripCost(double distance) {
        double ratePerKm = 12.0; // Rs. 12 per km
        double cost = distance * ratePerKm;
        FileLogger.log("CALCULATE TRIP COST: distance=" + distance
                + " km, cost=Rs. " + cost);
        return cost;
    }

    /**
     * Updates the status of a trip (e.g., Scheduled, In Progress, Completed).
     */
    public void updateTripStatus(int tripId, String newStatus) throws FleetException {
        String sql = "UPDATE Trip SET Status = ? WHERE Trip_ID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, tripId);
            int rows = stmt.executeUpdate();
            stmt.close();
            if (rows == 0) {
                throw new FleetException("Trip with ID " + tripId + " not found.");
            }
            FileLogger.log("UPDATE TRIP STATUS: ID " + tripId + " => " + newStatus);
            System.out.println("Trip status updated successfully!");
        } catch (SQLException e) {
            FileLogger.logError("UPDATE TRIP STATUS", e.getMessage());
            throw new FleetException("Error updating trip status: " + e.getMessage(), e);
        }
    }

    /**
     * Helper: builds a Trip from a ResultSet row.
     */
    private Trip buildTripFromResultSet(ResultSet rs) throws SQLException {
        return new Trip(
                rs.getInt("Trip_ID"),
                rs.getInt("Vehicle_ID"),
                rs.getInt("Driver_ID"),
                rs.getString("Departure_Date"),
                rs.getString("Arrival_Date"),
                rs.getDouble("Distance"),
                rs.getDouble("Cost"),
                rs.getString("Status")
        );
    }
}
