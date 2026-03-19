package com.fleetmgmt.controller;

import com.fleetmgmt.entity.DriverEntity;
import com.fleetmgmt.springservice.DriverSpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Driver REST Controller — manages driver CRUD operations.
 * Base URL: /api/drivers
 */
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverSpringService driverService;

    // GET /api/drivers — Get all drivers
    @GetMapping
    public List<DriverEntity> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    // POST /api/drivers — Add a new driver
    @PostMapping
    public ResponseEntity<DriverEntity> addDriver(@RequestBody DriverEntity driver) {
        DriverEntity saved = driverService.addDriver(driver);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/drivers/{id} — Update a driver
    @PutMapping("/{id}")
    public ResponseEntity<DriverEntity> updateDriver(
            @PathVariable("id") Integer id,
            @RequestBody DriverEntity driver) {
        DriverEntity updated = driverService.updateDriver(id, driver);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/drivers/{id} — Delete a driver
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDriver(@PathVariable("id") Integer id) {
        driverService.deleteDriver(id);
        return ResponseEntity.ok(Map.of("message", "Driver deleted successfully"));
    }
}
