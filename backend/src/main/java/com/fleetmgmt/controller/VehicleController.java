package com.fleetmgmt.controller;

import com.fleetmgmt.entity.VehicleEntity;
import com.fleetmgmt.springservice.VehicleSpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Vehicle REST Controller — manages vehicle CRUD operations.
 * Base URL: /api/vehicles
 */
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleSpringService vehicleService;

    // GET /api/vehicles — Get all vehicles
    @GetMapping
    public List<VehicleEntity> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    // POST /api/vehicles — Add a new vehicle
    @PostMapping
    public ResponseEntity<VehicleEntity> addVehicle(@RequestBody VehicleEntity vehicle) {
        VehicleEntity saved = vehicleService.addVehicle(vehicle);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/vehicles/{id}/status — Update vehicle status
    @PutMapping("/{id}/status")
    public ResponseEntity<VehicleEntity> updateStatus(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        VehicleEntity updated = vehicleService.updateVehicleStatus(id, newStatus);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/vehicles/{id} — Delete a vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteVehicle(@PathVariable("id") Integer id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(Map.of("message", "Vehicle deleted successfully"));
    }
}
