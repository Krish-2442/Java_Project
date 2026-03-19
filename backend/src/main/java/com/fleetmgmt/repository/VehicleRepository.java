package com.fleetmgmt.repository;

import com.fleetmgmt.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Vehicle Repository — provides CRUD + custom queries for Vehicle table.
 */
@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {

    // Find all vehicles by status (e.g., "Available", "In Service")
    List<VehicleEntity> findByStatus(String status);

    // Find all vehicles belonging to a specific fleet
    List<VehicleEntity> findByFleetId(Integer fleetId);
}
