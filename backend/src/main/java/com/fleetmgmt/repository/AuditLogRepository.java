package com.fleetmgmt.repository;

import com.fleetmgmt.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * AuditLog Repository — provides CRUD + ordering for Audit_Log table.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Integer> {

    // Get all logs, most recent first
    List<AuditLogEntity> findAllByOrderByActionDateDesc();
}
