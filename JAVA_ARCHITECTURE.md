# Fleet Management System - Java Files & Architecture

## Overview
This Fleet Management System follows a **layered architecture** with two parallel implementations:
1. **Spring Boot REST API** (for web/frontend)
2. **Console Application** (standalone Java application)

---

## Layer 1: Entry Points

### FleetManagementApplication.java
**Purpose:** Spring Boot application starter. Launches the embedded Tomcat server and starts the REST API on `http://localhost:8080`.

### Main.java
**Purpose:** Console-based menu system. Provides a text-driven interface for the standalone Java application demonstrating OOP concepts (inheritance, polymorphism, exception handling).

---

## Layer 2: Domain Models (Core Business Objects)

### Vehicle.java
**Purpose:** Abstract base class representing all vehicles. Defines common properties (registration number, make, model, year, fuel type, status).

### CarVehicle.java
**Purpose:** Concrete vehicle class extending Vehicle. Represents cars with specific attributes like fuel tank capacity.

### BusVehicle.java
**Purpose:** Concrete vehicle class extending Vehicle. Represents buses with specific attributes like seating capacity.

### Driver.java
**Purpose:** Represents a driver assigned to a fleet. Contains properties: driver ID, fleet ID, name, license number, contact, hire date, status.

### Fleet.java
**Purpose:** Represents a fleet (collection of vehicles). Contains properties: fleet ID, name, headquarters location, contact details.

### Trip.java
**Purpose:** Represents a vehicle trip/journey. Contains properties: trip ID, vehicle ID, driver ID, start/end locations, distance, date/time.

---

## Layer 3: JPA Entities (Database Mapping)

### VehicleEntity.java
**Purpose:** JPA entity mapped to the `Vehicle` table in MySQL. Stores vehicle data with annotations for database columns, relationships, and constraints. Can be either "Car" or "Bus" type via `vehicleType` field.

### DriverEntity.java
**Purpose:** JPA entity mapped to the `Driver` table. Stores driver information and links to fleet.

### FleetEntity.java
**Purpose:** JPA entity mapped to the `Fleet` table. Represents a fleet and links to multiple vehicles and drivers.

### TripEntity.java
**Purpose:** JPA entity mapped to the `Trip` table. Stores trip records with references to vehicle and driver.

### MaintenanceEntity.java
**Purpose:** JPA entity mapped to the `Maintenance` table. Tracks vehicle maintenance records (type, date, cost).

### AuditLogEntity.java
**Purpose:** JPA entity mapped to the `AuditLog` table. Records all system operations for history tracking.

---

## Layer 4: Database Access

### VehicleRepository.java
**Purpose:** Spring Data JPA repository for Vehicle table. Provides CRUD operations and custom queries:
- `findByStatus()` - Find vehicles by status
- `findByFleetId()` - Find all vehicles in a fleet

### DriverRepository.java
**Purpose:** Spring Data JPA repository for Driver table. Provides CRUD + custom queries for driver data.

### FleetRepository.java
**Purpose:** Spring Data JPA repository for Fleet table. Manages fleet data access.

### TripRepository.java
**Purpose:** Spring Data JPA repository for Trip table. Handles trip records access.

### MaintenanceRepository.java
**Purpose:** Spring Data JPA repository for Maintenance table. Manages maintenance records.

### AuditLogRepository.java
**Purpose:** Spring Data JPA repository for AuditLog table. Records and retrieves audit logs.

---

## Layer 5: Business Logic Services

### Spring-Based Services (for REST API)

#### VehicleSpringService.java
**Purpose:** Handles all vehicle business logic for Spring Boot API. Methods:
- `addVehicle()` - Register new vehicle
- `getAllVehicles()` - Retrieve all vehicles
- `updateVehicleStatus()` - Change vehicle status
- `deleteVehicle()` - Remove vehicle
- Auto-logs all operations to audit log

#### DriverSpringService.java
**Purpose:** Manages driver operations (register, update, delete, search).

#### FleetService.java
**Purpose:** Fleet management operations.

#### TripSpringService.java
**Purpose:** Trip management and tracking operations.

#### MaintenanceSpringService.java
**Purpose:** Maintenance record operations and scheduling.

#### AuditLogService.java
**Purpose:** Records all system operations with timestamp, user action, entity type, and description. Used by all other services for logging.

### JDBC-Based Services (for Console Application)

#### VehicleService.java
**Purpose:** Direct JDBC implementation for vehicle operations. Uses SQL queries to interact with database. Used by Main.java console app.

#### DriverService.java
**Purpose:** JDBC-based driver management (alternative to Spring).

#### FleetService.java
**Purpose:** JDBC-based fleet operations (alternative to Spring).

#### TripService.java
**Purpose:** JDBC-based trip management (alternative to Spring).

#### MaintenanceService.java
**Purpose:** JDBC-based maintenance tracking (alternative to Spring).

---

## Layer 6: REST API Controllers (HTTP Endpoints)

### VehicleController.java
**Purpose:** Handles HTTP requests for vehicle operations.
- **GET** `/api/vehicles` - List all vehicles
- **POST** `/api/vehicles` - Add new vehicle
- **PUT** `/api/vehicles/{id}/status` - Update vehicle status
- **DELETE** `/api/vehicles/{id}` - Remove vehicle

### DriverController.java
**Purpose:** REST endpoints for driver operations.

### FleetController.java
**Purpose:** REST endpoints for fleet management.

### TripController.java
**Purpose:** REST endpoints for trip tracking.

### MaintenanceController.java
**Purpose:** REST endpoints for maintenance records.

### AuditLogController.java
**Purpose:** REST endpoints for retrieving audit logs.

### DashboardController.java
**Purpose:** Serves dashboard statistics and summary data.

---

## Layer 7: Configuration & Utilities

### CorsConfig.java
**Purpose:** Configures Cross-Origin Resource Sharing (CORS) to allow requests from frontend to backend API.

### DatabaseConnection.java
**Purpose:** Utility class managing JDBC MySQL connection using singleton pattern. Provides a single shared database connection for the console application.

### FileLogger.java
**Purpose:** Logs application events to file for debugging and history tracking. Used by console application mainly.

### FleetException.java
**Purpose:** Custom exception class for Fleet Management specific errors. Used throughout services for error handling.

---

## Data Flow & Connections

### REST API Flow (Spring Boot):
```
Frontend (index.html) 
  ↓ HTTP Request
VehicleController (REST Endpoint)
  ↓ Call
VehicleSpringService (Business Logic)
  ↓ Uses Repository
VehicleRepository (Spring Data JPA)
  ↓ ORM Mapping
VehicleEntity (JPA Model)
  ↓ SQL Query
MySQL Database
  ↓ Returns
AuditLogService (Logs the operation)
```

### Console Application Flow:
```
Main.java (Menu System)
  ↓ Creates Objects
Vehicle/Driver/Fleet/Trip Models
  ↓ Calls Service
VehicleService (JDBC Based)
  ↓ Gets Connection from
DatabaseConnection (MySQL JDBC)
  ↓ Logs to
FileLogger (Local File)
  ↓ Throws (if errors)
FleetException (Error Handling)
```

---

## Key Relationships Between Files

| File | Depends On | Used By |
|------|-----------|---------|
| VehicleController | VehicleSpringService, VehicleEntity | HTTP Requests |
| VehicleSpringService | VehicleRepository, AuditLogService | VehicleController |
| VehicleRepository | VehicleEntity | VehicleSpringService |
| VehicleSpringService | VehicleRepository | REST Controllers |
| VehicleService | Vehicle, DatabaseConnection | Main.java |
| DatabaseConnection | (none) | All JDBC Services |
| FleetException | (none) | All Services |
| AuditLogService | AuditLogRepository | All Spring Services |

---

## File Categories Summary

**Controllers (HTTP Layer):**
- VehicleController, DriverController, FleetController, TripController, MaintenanceController, AuditLogController, DashboardController

**Spring Services (Business Logic - API):**
- VehicleSpringService, DriverSpringService, TripSpringService, MaintenanceSpringService, AuditLogService

**JDBC Services (Business Logic - Console):**
- VehicleService, DriverService, FleetService, TripService, MaintenanceService

**Repositories (Database Access):**
- VehicleRepository, DriverRepository, FleetRepository, TripRepository, MaintenanceRepository, AuditLogRepository

**Entities (Database Models):**
- VehicleEntity, DriverEntity, FleetEntity, TripEntity, MaintenanceEntity, AuditLogEntity

**Models (Domain Objects - Console):**
- Vehicle, CarVehicle, BusVehicle, Driver, Fleet, Trip

**Utilities & Config:**
- DatabaseConnection, FileLogger, CorsConfig, FleetException

**Entry Points:**
- FleetManagementApplication (Spring Boot), Main (Console)

---

## Architecture Pattern

This system uses **Layered Architecture** with clear separation of concerns:
1. **Presentation Layer:** Controllers handle HTTP requests
2. **Business Logic Layer:** Services contain all business operations
3. **Data Access Layer:** Repositories provide database operations
4. **Domain Layer:** Entities and Models represent business concepts
5. **Utility Layer:** Configuration and helper classes

The system supports **dual deployment**:
- **Web API:** Spring Boot handles REST requests from frontend
- **Console App:** Standalone menu for direct database operations
