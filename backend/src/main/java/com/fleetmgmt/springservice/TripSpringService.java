package com.fleetmgmt.springservice;

import com.fleetmgmt.entity.DriverEntity;
import com.fleetmgmt.entity.TripEntity;
import com.fleetmgmt.entity.VehicleEntity;
import com.fleetmgmt.repository.DriverRepository;
import com.fleetmgmt.repository.TripRepository;
import com.fleetmgmt.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Trip Service — handles trip creation with automatic cost calculation.
 * Business logic: Cost = Distance × ₹12/km
 * On trip creation: Vehicle → "In Service", Driver → "On Trip"
 * On trip completion: Vehicle → "Available", Driver → "Available"
 */
@Service
public class TripSpringService {

    // Rate per kilometer in Rupees
    private static final double RATE_PER_KM = 12.0;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private AuditLogService auditLogService;

    /**
     * Create a new trip with automatic cost calculation.
     * Also updates vehicle and driver statuses.
     */
    @Transactional
    public TripEntity createTrip(TripEntity trip) {
        // Validate vehicle exists and is available
        VehicleEntity vehicle = vehicleRepository.findById(trip.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle with ID " + trip.getVehicleId() + " not found"));

        // Validate driver exists and is available
        DriverEntity driver = driverRepository.findById(trip.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver with ID " + trip.getDriverId() + " not found"));

        // Auto-calculate cost: distance × ₹12 per km
        double cost = trip.getDistance() * RATE_PER_KM;
        trip.setCost(cost);
        trip.setStatus("Scheduled");

        if (trip.getDepartureDate() == null) {
            trip.setDepartureDate(LocalDate.now());
        }

        // Save the trip
        TripEntity saved = tripRepository.save(trip);

        // Update vehicle status to "In Service"
        vehicle.setStatus("In Service");
        vehicleRepository.save(vehicle);

        // Update driver status to "On Trip"
        driver.setStatus("On Trip");
        driverRepository.save(driver);

        auditLogService.log("INSERT", "Trip", saved.getTripId(),
                "Trip created: Vehicle " + trip.getVehicleId()
                        + ", Driver " + trip.getDriverId()
                        + ", Distance " + trip.getDistance() + " km"
                        + ", Cost ₹" + String.format("%.2f", cost));

        return saved;
    }

    /**
     * Get all trips from the database.
     */
    public List<TripEntity> getAllTrips() {
        return tripRepository.findAll();
    }

    /**
     * Complete a trip — sets arrival date, frees vehicle and driver.
     */
    @Transactional
    public TripEntity completeTrip(Integer tripId) {
        TripEntity trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip with ID " + tripId + " not found"));

        trip.setStatus("Completed");
        trip.setArrivalDate(LocalDate.now());
        TripEntity saved = tripRepository.save(trip);

        // Free the vehicle
        VehicleEntity vehicle = vehicleRepository.findById(trip.getVehicleId()).orElse(null);
        if (vehicle != null) {
            vehicle.setStatus("Available");
            vehicleRepository.save(vehicle);
        }

        // Free the driver
        DriverEntity driver = driverRepository.findById(trip.getDriverId()).orElse(null);
        if (driver != null) {
            driver.setStatus("Available");
            driverRepository.save(driver);
        }

        auditLogService.log("UPDATE", "Trip", tripId, "Trip completed");
        return saved;
    }
}
