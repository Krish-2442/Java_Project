package com.fleetmgmt.springservice;

import com.fleetmgmt.entity.AuditLogEntity;
import com.fleetmgmt.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AuditLog Service — central logging for all system actions.
 * Every CRUD operation across the system calls this service
 * to maintain a complete audit trail in the database.
 */
@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    /**
     * Logs an action to the Audit_Log table.
     */
    public void log(String actionType, String tableName, Integer recordId, String description) {
        AuditLogEntity logEntry = new AuditLogEntity(actionType, tableName, recordId, description);
        auditLogRepository.save(logEntry);
    }

    /**
     * Returns all audit logs, most recent first.
     */
    public List<AuditLogEntity> getAllLogs() {
        return auditLogRepository.findAllByOrderByActionDateDesc();
    }
}
