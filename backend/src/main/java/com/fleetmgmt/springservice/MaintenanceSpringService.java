package com.fleetmgmt.springservice;

import com.fleetmgmt.entity.MaintenanceEntity;
import com.fleetmgmt.entity.VehicleEntity;
import com.fleetmgmt.repository.MaintenanceRepository;
import com.fleetmgmt.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Maintenance Service — handles maintenance scheduling and completion.
 * On scheduling: creates a maintenance record with status "Scheduled".
 * On completion: marks as "Completed" and sets vehicle to "Available".
 */
@Service
public class MaintenanceSpringService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AuditLogService auditLogService;

    /**
     * Schedule a new maintenance entry for a vehicle.
     */
    public MaintenanceEntity scheduleMaintenance(MaintenanceEntity maintenance) {
        maintenance.setStatus("Scheduled");

        // Optionally set vehicle to "Under Maintenance"
        VehicleEntity vehicle = vehicleRepository.findById(maintenance.getVehicleId()).orElse(null);
        if (vehicle != null) {
            vehicle.setStatus("Under Maintenance");
            vehicleRepository.save(vehicle);
        }

        MaintenanceEntity saved = maintenanceRepository.save(maintenance);

        auditLogService.log("INSERT", "Maintenance", saved.getMaintenanceId(),
                "Maintenance scheduled: Vehicle " + maintenance.getVehicleId()
                        + " — " + maintenance.getMaintenanceType());
        return saved;
    }

    /**
     * Get all maintenance records.
     */
    public List<MaintenanceEntity> getAllMaintenance() {
        return maintenanceRepository.findAll();
    }

    /**
     * Get maintenance alerts (Scheduled and In Progress records).
     */
    public List<MaintenanceEntity> getMaintenanceAlerts() {
        List<MaintenanceEntity> alerts = maintenanceRepository.findByStatus("Scheduled");
        alerts.addAll(maintenanceRepository.findByStatus("In Progress"));
        return alerts;
    }

    /**
     * Mark a maintenance record as Completed.
     * Also sets the vehicle status back to "Available".
     */
    @Transactional
    public MaintenanceEntity completeMaintenance(Integer maintenanceId) {
        MaintenanceEntity maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance record " + maintenanceId + " not found"));

        maintenance.setStatus("Completed");
        MaintenanceEntity saved = maintenanceRepository.save(maintenance);

        // Set vehicle back to Available
        VehicleEntity vehicle = vehicleRepository.findById(maintenance.getVehicleId()).orElse(null);
        if (vehicle != null) {
            vehicle.setStatus("Available");
            vehicleRepository.save(vehicle);
        }

        auditLogService.log("UPDATE", "Maintenance", maintenanceId,
                "Maintenance completed for Vehicle " + maintenance.getVehicleId());
        return saved;
    }
}
