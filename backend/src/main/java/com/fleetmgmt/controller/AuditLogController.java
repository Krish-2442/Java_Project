package com.fleetmgmt.controller;

import com.fleetmgmt.entity.AuditLogEntity;
import com.fleetmgmt.springservice.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AuditLog REST Controller — provides access to system activity logs.
 * Base URL: /api/logs
 */
@RestController
@RequestMapping("/api/logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    // GET /api/logs — Get all logs (most recent first)
    @GetMapping
    public List<AuditLogEntity> getAllLogs() {
        return auditLogService.getAllLogs();
    }
}
