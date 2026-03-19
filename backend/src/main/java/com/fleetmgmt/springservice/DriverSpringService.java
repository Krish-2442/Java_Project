package com.fleetmgmt.springservice;

import com.fleetmgmt.entity.DriverEntity;
import com.fleetmgmt.repository.DriverRepository;
import com.fleetmgmt.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Driver Service — handles all driver business logic.
 * Supports hiring, updating, and retrieving drivers with audit logging.
 */
@Service
public class DriverSpringService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private AuditLogService auditLogService;

    /**
     * Hire (add) a new driver.
     */
    public DriverEntity addDriver(DriverEntity driver) {
        if (driver.getStatus() == null || driver.getStatus().isEmpty()) {
            driver.setStatus("Available");
        }
        DriverEntity saved = driverRepository.save(driver);
        auditLogService.log("INSERT", "Driver", saved.getDriverId(),
                "New driver hired: " + saved.getName() + " (License: " + saved.getLicenseNumber() + ")");
        return saved;
    }

    /**
     * Get all drivers from the database.
     */
    public List<DriverEntity> getAllDrivers() {
        return driverRepository.findAll();
    }

    /**
     * Update a driver's details (name, phone, status).
     */
    public DriverEntity updateDriver(Integer driverId, DriverEntity updated) {
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver with ID " + driverId + " not found"));

        if (updated.getName() != null) driver.setName(updated.getName());
        if (updated.getPhone() != null) driver.setPhone(updated.getPhone());
        if (updated.getStatus() != null) driver.setStatus(updated.getStatus());

        DriverEntity saved = driverRepository.save(driver);
        auditLogService.log("UPDATE", "Driver", driverId,
                "Updated driver: " + saved.getName());
        return saved;
    }

    /**
     * Get all available drivers.
     */
    public List<DriverEntity> getAvailableDrivers() {
        return driverRepository.findByStatus("Available");
    }

    /**
     * Delete a driver by ID.
     * Cascade deletes all related trips first.
     */
    public void deleteDriver(Integer driverId) {
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver with ID " + driverId + " not found"));

        // Delete all trips associated with this driver
        tripRepository.deleteAll(tripRepository.findByDriverId(driverId));

        // Finally delete the driver
        driverRepository.deleteById(driverId);
        auditLogService.log("DELETE", "Driver", driverId,
                "Deleted driver: " + driver.getName() + " (with cascade delete)");
    }
}
