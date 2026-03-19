package com.fleetmgmt.controller;

import com.fleetmgmt.entity.FleetEntity;
import com.fleetmgmt.repository.FleetRepository;
import com.fleetmgmt.springservice.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Fleet REST Controller — manages fleet CRUD operations.
 * Base URL: /api/fleets
 */
@RestController
@RequestMapping("/api/fleets")
public class FleetController {

    @Autowired
    private FleetRepository fleetRepository;

    @Autowired
    private AuditLogService auditLogService;

    // GET /api/fleets — Get all fleets
    @GetMapping
    public List<FleetEntity> getAllFleets() {
        return fleetRepository.findAll();
    }

    // POST /api/fleets — Add a new fleet
    @PostMapping
    public ResponseEntity<FleetEntity> addFleet(@RequestBody FleetEntity fleet) {
        FleetEntity saved = fleetRepository.save(fleet);
        auditLogService.log("INSERT", "Fleet", saved.getFleetId(),
                "Fleet added: " + saved.getFleetName());
        return ResponseEntity.ok(saved);
    }
}
