package com.fleetmgmt.controller;

import com.fleetmgmt.entity.MaintenanceEntity;
import com.fleetmgmt.springservice.MaintenanceSpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Maintenance REST Controller — manages maintenance scheduling and alerts.
 * Base URL: /api/maintenance
 */
@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceSpringService maintenanceService;

    // GET /api/maintenance — Get all maintenance records
    @GetMapping
    public List<MaintenanceEntity> getAllMaintenance() {
        return maintenanceService.getAllMaintenance();
    }

    // GET /api/maintenance/alerts — Get active maintenance alerts
    @GetMapping("/alerts")
    public List<MaintenanceEntity> getAlerts() {
        return maintenanceService.getMaintenanceAlerts();
    }

    // POST /api/maintenance — Schedule new maintenance
    @PostMapping
    public ResponseEntity<MaintenanceEntity> scheduleMaintenance(@RequestBody MaintenanceEntity maintenance) {
        MaintenanceEntity saved = maintenanceService.scheduleMaintenance(maintenance);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/maintenance/{id}/complete — Mark maintenance as completed
    @PutMapping("/{id}/complete")
    public ResponseEntity<MaintenanceEntity> completeMaintenance(@PathVariable("id") Integer id) {
        MaintenanceEntity completed = maintenanceService.completeMaintenance(id);
        return ResponseEntity.ok(completed);
    }
}
