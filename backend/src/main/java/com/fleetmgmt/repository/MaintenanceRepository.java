package com.fleetmgmt.repository;

import com.fleetmgmt.entity.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Maintenance Repository — provides CRUD + custom queries for Maintenance table.
 */
@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Integer> {

    // Find maintenance records by status (e.g., "Scheduled", "In Progress")
    List<MaintenanceEntity> findByStatus(String status);

    // Find all maintenance records for a specific vehicle
    List<MaintenanceEntity> findByVehicleId(Integer vehicleId);
}
