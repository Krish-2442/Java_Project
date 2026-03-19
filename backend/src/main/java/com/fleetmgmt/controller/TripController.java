package com.fleetmgmt.controller;

import com.fleetmgmt.entity.TripEntity;
import com.fleetmgmt.springservice.TripSpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Trip REST Controller — manages trip creation and history.
 * Base URL: /api/trips
 */
@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripSpringService tripService;

    // GET /api/trips — Get all trips (history)
    @GetMapping
    public List<TripEntity> getAllTrips() {
        return tripService.getAllTrips();
    }

    // POST /api/trips — Create a new trip (auto-calculates cost at ₹12/km)
    @PostMapping
    public ResponseEntity<TripEntity> createTrip(@RequestBody TripEntity trip) {
        TripEntity saved = tripService.createTrip(trip);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/trips/{id}/complete — Complete a trip
    @PutMapping("/{id}/complete")
    public ResponseEntity<TripEntity> completeTrip(@PathVariable("id") Integer id) {
        TripEntity completed = tripService.completeTrip(id);
        return ResponseEntity.ok(completed);
    }
}
