package com.fleetmgmt.repository;

import com.fleetmgmt.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Trip Repository — provides CRUD operations for Trip table.
 */
@Repository
public interface TripRepository extends JpaRepository<TripEntity, Integer> {

    // Find all trips for a specific vehicle
    List<TripEntity> findByVehicleId(Integer vehicleId);

    // Find all trips for a specific driver
    List<TripEntity> findByDriverId(Integer driverId);
}
