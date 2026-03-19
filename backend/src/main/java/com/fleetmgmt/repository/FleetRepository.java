package com.fleetmgmt.repository;

import com.fleetmgmt.entity.FleetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Fleet Repository — provides CRUD operations for Fleet table.
 * Spring Data JPA auto-generates the implementation.
 */
@Repository
public interface FleetRepository extends JpaRepository<FleetEntity, Integer> {
}
