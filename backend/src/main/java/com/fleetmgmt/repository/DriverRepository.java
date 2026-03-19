package com.fleetmgmt.repository;

import com.fleetmgmt.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Driver Repository — provides CRUD + custom queries for Driver table.
 */
@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {

    // Find all drivers by status
    List<DriverEntity> findByStatus(String status);

    // Find all drivers belonging to a specific fleet
    List<DriverEntity> findByFleetId(Integer fleetId);
}
