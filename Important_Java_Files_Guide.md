# Important Java Files Guide

The project will be reorganized into a clean, easy-to-read guide that points you exactly to the pure Java files, skipping all the web and database overhead.

## User Action Required

> [!IMPORTANT]
> **Pure Java Start Point:** The absolute starting point of your application is the `Main.java` file. It contains your `public static void main` method and the interactive console loop.
> - **File:** `Main.java`
> - **Path:** `backend/src/main/java/com/fleetmgmt/Main.java`

> [!NOTE]
> **Web & SQL Files:** Since your project is strictly Core Java, do NOT use `FleetManagementApplication.java` because it attempts to start a full Spring Boot web server which you said you do not want.

## Core OOP Models

> [!TIP]
> **Object-Oriented Programming:** These files are pure Java classes that define real-world objects using Inheritance, Polymorphism, and Encapsulation.

### Object Classes
Here are your core data structures:
- `Vehicle.java` *(Parent class)*
- `CarVehicle.java` & `BusVehicle.java` *(Child classes)*
- `Driver.java`
- `Fleet.java`
- `Trip.java`

**Location:** `backend/src/main/java/com/fleetmgmt/model/`

---

## Business Logic

> [!TIP]
> **Services:** These files contain pure Java logic (handling ArrayLists, calculating costs, managing objects) without any SQL or Web code.

### Logic Classes
- `DriverService.java`
- `FleetService.java`
- `MaintenanceService.java`
- `TripService.java`
- `VehicleService.java`

**Location:** `backend/src/main/java/com/fleetmgmt/service/`

---

## Utilities & Exceptions

> [!NOTE]
> **Extras:** Standard Java exception handling and file I/O operations.
- **Exception Handling:** `backend/src/main/java/com/fleetmgmt/exception/FleetException.java`
- **File Handling:** `backend/src/main/java/com/fleetmgmt/util/FileLogger.java`

---

## ❌ Files to Ignore

> [!WARNING]
> Completely ignore the following directories inside `backend/src/main/java/com/fleetmgmt/` as they contain Spring Boot (Web) and JPA (SQL) code:

- `controller/` directory *(REST APIs)*
- `entity/` directory *(Database Tables)*
- `repository/` directory *(SQL Queries)*
- `springservice/` directory *(Web Logic)*
- `DatabaseConnection.java` *(SQL configuration)*
