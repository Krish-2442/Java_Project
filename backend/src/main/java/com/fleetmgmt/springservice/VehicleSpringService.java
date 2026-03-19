package com.fleetmgmt.springservice;

import com.fleetmgmt.entity.VehicleEntity;
import com.fleetmgmt.repository.VehicleRepository;
import com.fleetmgmt.repository.TripRepository;
import com.fleetmgmt.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Vehicle Service — handles all vehicle business logic.
 * Supports CRUD operations with audit logging.
 */
@Service
public class VehicleSpringService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private AuditLogService auditLogService;

    /**
     * Add a new vehicle to the database.
     */
    public VehicleEntity addVehicle(VehicleEntity vehicle) {
        vehicle.setStatus("Available"); // new vehicles start as Available
        VehicleEntity saved = vehicleRepository.save(vehicle);
        auditLogService.log("INSERT", "Vehicle", saved.getVehicleId(),
                "Registered vehicle: " + saved.getRegistrationNumber()
                        + " (" + saved.getMake() + " " + saved.getModel() + ")");
        return saved;
    }

    /**
     * Get all vehicles from the database.
     */
    public List<VehicleEntity> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    /**
     * Update the status of a vehicle.
     * Valid statuses: Available, In Service, Under Maintenance
     */
    public VehicleEntity updateVehicleStatus(Integer vehicleId, String newStatus) {
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle with ID " + vehicleId + " not found"));

        String oldStatus = vehicle.getStatus();
        vehicle.setStatus(newStatus);
        VehicleEntity updated = vehicleRepository.save(vehicle);

        auditLogService.log("UPDATE", "Vehicle", vehicleId,
                "Status changed: " + oldStatus + " → " + newStatus);
        return updated;
    }

    /**
     * Delete a vehicle by ID.
     * Cascade deletes all related trips and maintenance records first.
     */
    public void deleteVehicle(Integer vehicleId) {
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle with ID " + vehicleId + " not found"));

        // Delete all trips associated with this vehicle
        List<Integer> tripIds = tripRepository.findByVehicleId(vehicleId)
                .stream()
                .map(t -> t.getTripId())
                .toList();
        tripIds.forEach(tripId -> {
            tripRepository.deleteById(tripId);
        });

        // Delete all maintenance records associated with this vehicle
        maintenanceRepository.deleteAll(maintenanceRepository.findByVehicleId(vehicleId));

        // Finally delete the vehicle
        vehicleRepository.deleteById(vehicleId);
        auditLogService.log("DELETE", "Vehicle", vehicleId,
                "Deleted vehicle: " + vehicle.getRegistrationNumber() + " (with cascade delete)");
    }

    /**
     * Get vehicles by fleet ID.
     */
    public List<VehicleEntity> getVehiclesByFleet(Integer fleetId) {
        return vehicleRepository.findByFleetId(fleetId);
    }
}
