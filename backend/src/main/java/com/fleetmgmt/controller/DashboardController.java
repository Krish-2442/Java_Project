package com.fleetmgmt.controller;

import com.fleetmgmt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Dashboard REST Controller — provides aggregated stats for the dashboard.
 * Base URL: /api/dashboard
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private DriverRepository driverRepository;
    @Autowired private TripRepository tripRepository;
    @Autowired private MaintenanceRepository maintenanceRepository;

    // GET /api/dashboard/stats — Get dashboard statistics
    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalVehicles", vehicleRepository.count());
        stats.put("totalDrivers", driverRepository.count());
        stats.put("totalTrips", tripRepository.count());
        stats.put("totalMaintenance", maintenanceRepository.count());

        // Count by status
        stats.put("availableVehicles", vehicleRepository.findByStatus("Available").size());
        stats.put("inServiceVehicles", vehicleRepository.findByStatus("In Service").size());
        stats.put("maintenanceAlerts", maintenanceRepository.findByStatus("Scheduled").size()
                + maintenanceRepository.findByStatus("In Progress").size());
        stats.put("availableDrivers", driverRepository.findByStatus("Available").size());

        return stats;
    }
}
