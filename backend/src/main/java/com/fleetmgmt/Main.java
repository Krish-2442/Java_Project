package com.fleetmgmt;

import com.fleetmgmt.exception.FleetException;
import com.fleetmgmt.model.*;
import com.fleetmgmt.service.*;
import com.fleetmgmt.util.DatabaseConnection;
import com.fleetmgmt.util.FileLogger;

import java.util.List;
import java.util.Scanner;

/**
 * Main application class — console-based menu for the Fleet Management System.
 * Demonstrates:
 *  - OOP (objects, constructors)
 *  - Inheritance (Vehicle → CarVehicle, BusVehicle)
 *  - Polymorphism (List<Vehicle> holding different types)
 *  - Exception Handling (try-catch with FleetException)
 *  - File Handling (FileLogger)
 *  - JDBC Database Connectivity
 *  - Simple Threading (optional maintenance monitor)
 */
public class Main {

    // Service instances
    private static final FleetService fleetService = new FleetService();
    private static final VehicleService vehicleService = new VehicleService();
    private static final DriverService driverService = new DriverService();
    private static final TripService tripService = new TripService();
    private static final MaintenanceService maintenanceService = new MaintenanceService();

    // Scanner for console input
    private static final Scanner scanner = new Scanner(System.in);

    // ===== MAIN METHOD =====
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   FLEET MANAGEMENT SYSTEM (v1.0)");
        System.out.println("=========================================");
        FileLogger.log("APPLICATION STARTED");

        // Start optional background maintenance monitor thread
        startMaintenanceMonitor();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: manageFleets(); break;
                case 2: manageVehicles(); break;
                case 3: manageDrivers(); break;
                case 4: createTrip(); break;
                case 5: scheduleMaintenance(); break;
                case 6: viewReports(); break;
                case 7:
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                    FileLogger.log("APPLICATION STOPPED");
                    DatabaseConnection.closeConnection();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    // ==================== MAIN MENU ====================
    private static void printMainMenu() {
        System.out.println("\n----- MAIN MENU -----");
        System.out.println("1. Manage Fleets");
        System.out.println("2. Manage Vehicles");
        System.out.println("3. Manage Drivers");
        System.out.println("4. Create Trip");
        System.out.println("5. Schedule Maintenance");
        System.out.println("6. View Reports");
        System.out.println("7. Exit");
        System.out.println("---------------------");
    }

    // ==================== 1. MANAGE FLEETS ====================
    private static void manageFleets() {
        System.out.println("\n--- Manage Fleets ---");
        System.out.println("1. Add Fleet");
        System.out.println("2. View All Fleets");
        System.out.println("3. Update Fleet");
        System.out.println("4. Back to Main Menu");

        int choice = readInt("Enter your choice: ");
        try {
            switch (choice) {
                case 1: addFleet(); break;
                case 2: viewAllFleets(); break;
                case 3: updateFleet(); break;
                case 4: break;
                default: System.out.println("Invalid choice.");
            }
        } catch (FleetException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addFleet() throws FleetException {
        System.out.print("Enter Fleet Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Company Name: ");
        String company = scanner.nextLine();
        System.out.print("Enter Contact Phone: ");
        String phone = scanner.nextLine();

        Fleet fleet = new Fleet(0, name, company, phone);
        fleetService.addFleet(fleet);
    }

    private static void viewAllFleets() throws FleetException {
        List<Fleet> fleets = fleetService.getAllFleets();
        if (fleets.isEmpty()) {
            System.out.println("No fleets found.");
            return;
        }
        System.out.println("\n--- All Fleets ---");
        System.out.printf("%-5s %-20s %-20s %-15s%n",
                "ID", "Fleet Name", "Company", "Phone");
        System.out.println("-".repeat(65));
        for (Fleet f : fleets) {
            System.out.printf("%-5d %-20s %-20s %-15s%n",
                    f.getFleetId(), f.getFleetName(),
                    f.getCompanyName(), f.getContactPhone());
        }
    }

    private static void updateFleet() throws FleetException {
        int id = readInt("Enter Fleet ID to update: ");
        Fleet existing = fleetService.getFleetById(id);
        System.out.println("Current: " + existing);

        System.out.print("New Fleet Name (or press Enter to keep): ");
        String name = scanner.nextLine();
        System.out.print("New Company Name (or press Enter to keep): ");
        String company = scanner.nextLine();
        System.out.print("New Contact Phone (or press Enter to keep): ");
        String phone = scanner.nextLine();

        existing.setFleetName(name.isEmpty() ? existing.getFleetName() : name);
        existing.setCompanyName(company.isEmpty() ? existing.getCompanyName() : company);
        existing.setContactPhone(phone.isEmpty() ? existing.getContactPhone() : phone);
        fleetService.updateFleet(existing);
    }

    // ==================== 2. MANAGE VEHICLES ====================
    private static void manageVehicles() {
        System.out.println("\n--- Manage Vehicles ---");
        System.out.println("1. Register Vehicle");
        System.out.println("2. View Vehicles of Fleet");
        System.out.println("3. Update Vehicle Status");
        System.out.println("4. Delete Vehicle");
        System.out.println("5. Back to Main Menu");

        int choice = readInt("Enter your choice: ");
        try {
            switch (choice) {
                case 1: registerVehicle(); break;
                case 2: viewVehiclesByFleet(); break;
                case 3: updateVehicleStatus(); break;
                case 4: deleteVehicle(); break;
                case 5: break;
                default: System.out.println("Invalid choice.");
            }
        } catch (FleetException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerVehicle() throws FleetException {
        int fleetId = readInt("Enter Fleet ID: ");
        System.out.print("Enter Registration Number: ");
        String regNum = scanner.nextLine();
        System.out.print("Enter Make (e.g., Tata): ");
        String make = scanner.nextLine();
        System.out.print("Enter Model (e.g., Nexon): ");
        String model = scanner.nextLine();
        int year = readInt("Enter Year: ");
        System.out.print("Enter Fuel Type (Petrol/Diesel/CNG): ");
        String fuel = scanner.nextLine();
        System.out.print("Enter Vehicle Type (Car/Bus): ");
        String type = scanner.nextLine();

        Vehicle vehicle;
        if ("Bus".equalsIgnoreCase(type)) {
            int seats = readInt("Enter Seating Capacity: ");
            vehicle = new BusVehicle(0, fleetId, regNum, make, model,
                    year, fuel, "Available", seats);
        } else {
            vehicle = new CarVehicle(0, fleetId, regNum, make, model,
                    year, fuel, "Available");
        }
        vehicleService.registerVehicle(vehicle);
    }

    private static void viewVehiclesByFleet() throws FleetException {
        int fleetId = readInt("Enter Fleet ID: ");
        List<Vehicle> vehicles = vehicleService.getVehiclesByFleet(fleetId);
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found for Fleet ID " + fleetId);
            return;
        }
        System.out.println("\n--- Vehicles in Fleet " + fleetId + " ---");
        System.out.printf("%-5s %-12s %-10s %-10s %-6s %-8s %-6s %-15s%n",
                "ID", "Reg No.", "Make", "Model", "Year", "Fuel", "Type", "Status");
        System.out.println("-".repeat(80));
        // POLYMORPHISM: each item is a Vehicle reference but actual type varies
        for (Vehicle v : vehicles) {
            System.out.printf("%-5d %-12s %-10s %-10s %-6d %-8s %-6s %-15s%n",
                    v.getVehicleId(), v.getRegistrationNumber(),
                    v.getMake(), v.getModel(), v.getYear(),
                    v.getFuelType(), v.getVehicleType(), v.getStatus());
        }
    }

    private static void updateVehicleStatus() throws FleetException {
        int id = readInt("Enter Vehicle ID: ");
        System.out.print("New Status (Available/In Service/Under Maintenance): ");
        String status = scanner.nextLine();
        vehicleService.updateVehicleStatus(id, status);
    }

    private static void deleteVehicle() throws FleetException {
        int id = readInt("Enter Vehicle ID to delete: ");
        vehicleService.deleteVehicle(id);
    }

    // ==================== 3. MANAGE DRIVERS ====================
    private static void manageDrivers() {
        System.out.println("\n--- Manage Drivers ---");
        System.out.println("1. Hire Driver");
        System.out.println("2. View All Drivers");
        System.out.println("3. View Drivers by Fleet");
        System.out.println("4. Update Driver Status");
        System.out.println("5. Back to Main Menu");

        int choice = readInt("Enter your choice: ");
        try {
            switch (choice) {
                case 1: hireDriver(); break;
                case 2: viewAllDrivers(); break;
                case 3: viewDriversByFleet(); break;
                case 4: updateDriverStatus(); break;
                case 5: break;
                default: System.out.println("Invalid choice.");
            }
        } catch (FleetException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void hireDriver() throws FleetException {
        int fleetId = readInt("Enter Fleet ID: ");
        System.out.print("Enter Driver Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter License Number: ");
        String license = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Hire Date (YYYY-MM-DD): ");
        String hireDate = scanner.nextLine();

        Driver driver = new Driver(0, fleetId, name, license,
                phone, hireDate, "Available");
        driverService.hireDriver(driver);
    }

    private static void viewAllDrivers() throws FleetException {
        List<Driver> drivers = driverService.getAllDrivers();
        if (drivers.isEmpty()) {
            System.out.println("No drivers found.");
            return;
        }
        System.out.println("\n--- All Drivers ---");
        System.out.printf("%-5s %-5s %-20s %-20s %-12s %-12s %-10s%n",
                "ID", "Fleet", "Name", "License No.", "Phone", "Hire Date", "Status");
        System.out.println("-".repeat(90));
        for (Driver d : drivers) {
            System.out.printf("%-5d %-5d %-20s %-20s %-12s %-12s %-10s%n",
                    d.getDriverId(), d.getFleetId(), d.getName(),
                    d.getLicenseNumber(), d.getPhone(),
                    d.getHireDate(), d.getStatus());
        }
    }

    private static void viewDriversByFleet() throws FleetException {
        int fleetId = readInt("Enter Fleet ID: ");
        List<Driver> drivers = driverService.getDriversByFleet(fleetId);
        if (drivers.isEmpty()) {
            System.out.println("No drivers found for Fleet ID " + fleetId);
            return;
        }
        System.out.println("\n--- Drivers in Fleet " + fleetId + " ---");
        for (Driver d : drivers) {
            System.out.println(d);
        }
    }

    private static void updateDriverStatus() throws FleetException {
        int id = readInt("Enter Driver ID: ");
        System.out.print("Enter Driver Name (or press Enter to keep): ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone (or press Enter to keep): ");
        String phone = scanner.nextLine();
        System.out.print("New Status (Available/On Trip/On Leave): ");
        String status = scanner.nextLine();

        Driver driver = new Driver();
        driver.setDriverId(id);
        driver.setName(name.isEmpty() ? "N/A" : name);
        driver.setPhone(phone.isEmpty() ? "N/A" : phone);
        driver.setStatus(status);
        driverService.updateDriver(driver);
    }

    // ==================== 4. CREATE TRIP ====================
    private static void createTrip() {
        System.out.println("\n--- Create Trip ---");
        try {
            int vehicleId = readInt("Enter Vehicle ID: ");
            int driverId = readInt("Enter Driver ID: ");
            System.out.print("Enter Departure Date (YYYY-MM-DD): ");
            String depDate = scanner.nextLine();
            double distance = readDouble("Enter Distance (km): ");

            // Auto-calculate cost
            double cost = tripService.calculateTripCost(distance);
            System.out.printf("Estimated Cost: Rs. %.2f%n", cost);

            Trip trip = new Trip(0, vehicleId, driverId, depDate,
                    null, distance, cost, "Scheduled");
            tripService.createTrip(trip);
        } catch (FleetException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== 5. SCHEDULE MAINTENANCE ====================
    private static void scheduleMaintenance() {
        System.out.println("\n--- Schedule Maintenance ---");
        try {
            int vehicleId = readInt("Enter Vehicle ID: ");
            System.out.print("Enter Maintenance Type (Oil Change/Tire Replacement/Engine Repair/etc.): ");
            String type = scanner.nextLine();
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();
            double cost = readDouble("Enter Estimated Cost (Rs.): ");
            maintenanceService.scheduleMaintenance(vehicleId, type, date, cost);
        } catch (FleetException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== 6. VIEW REPORTS ====================
    private static void viewReports() {
        System.out.println("\n--- Reports ---");
        System.out.println("1. All Trips Report");
        System.out.println("2. Vehicle Status Report");
        System.out.println("3. Driver List");
        System.out.println("4. Maintenance Report by Vehicle");
        System.out.println("5. Back to Main Menu");

        int choice = readInt("Enter your choice: ");
        try {
            switch (choice) {
                case 1: showAllTripsReport(); break;
                case 2: showVehicleStatusReport(); break;
                case 3: viewAllDrivers(); break;
                case 4: showMaintenanceReport(); break;
                case 5: break;
                default: System.out.println("Invalid choice.");
            }
        } catch (FleetException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showAllTripsReport() throws FleetException {
        List<Trip> trips = tripService.getAllTrips();
        if (trips.isEmpty()) {
            System.out.println("No trips found.");
            return;
        }
        System.out.println("\n========= ALL TRIPS REPORT =========");
        System.out.printf("%-5s %-8s %-8s %-12s %-12s %-10s %-10s %-12s%n",
                "ID", "Vehicle", "Driver", "Departure", "Arrival",
                "Dist(km)", "Cost(Rs)", "Status");
        System.out.println("-".repeat(85));
        double totalCost = 0;
        double totalDistance = 0;
        for (Trip t : trips) {
            String arrival = (t.getArrivalDate() == null) ? "—" : t.getArrivalDate();
            System.out.printf("%-5d %-8d %-8d %-12s %-12s %-10.1f %-10.2f %-12s%n",
                    t.getTripId(), t.getVehicleId(), t.getDriverId(),
                    t.getDepartureDate(), arrival,
                    t.getDistance(), t.getCost(), t.getStatus());
            totalCost += t.getCost();
            totalDistance += t.getDistance();
        }
        System.out.println("-".repeat(85));
        System.out.printf("TOTAL: %d trips | %.1f km | Rs. %.2f%n",
                trips.size(), totalDistance, totalCost);
    }

    private static void showVehicleStatusReport() throws FleetException {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }
        System.out.println("\n========= VEHICLE STATUS REPORT =========");
        System.out.printf("%-5s %-12s %-10s %-10s %-6s %-8s %-6s %-15s%n",
                "ID", "Reg No.", "Make", "Model", "Year", "Fuel", "Type", "Status");
        System.out.println("-".repeat(80));
        // POLYMORPHISM: printing different vehicle types via common reference
        for (Vehicle v : vehicles) {
            System.out.printf("%-5d %-12s %-10s %-10s %-6d %-8s %-6s %-15s%n",
                    v.getVehicleId(), v.getRegistrationNumber(),
                    v.getMake(), v.getModel(), v.getYear(),
                    v.getFuelType(), v.getVehicleType(), v.getStatus());
        }
    }

    private static void showMaintenanceReport() throws FleetException {
        int vehicleId = readInt("Enter Vehicle ID: ");
        List<String> records = maintenanceService.getMaintenanceByVehicle(vehicleId);
        if (records.isEmpty()) {
            System.out.println("No maintenance records for Vehicle ID " + vehicleId);
            return;
        }
        System.out.println("\n--- Maintenance Records for Vehicle " + vehicleId + " ---");
        for (String record : records) {
            System.out.println(record);
        }
    }

    // ==================== BACKGROUND THREAD (OPTIONAL) ====================
    /**
     * Starts a background daemon thread that periodically checks for vehicles
     * under maintenance and prints an alert. Demonstrates simple THREADING.
     */
    private static void startMaintenanceMonitor() {
        Thread monitor = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000); // check every 30 seconds
                    List<Vehicle> vehicles = vehicleService.getAllVehicles();
                    for (Vehicle v : vehicles) {
                        if ("Under Maintenance".equalsIgnoreCase(v.getStatus())) {
                            System.out.println("\n[ALERT] Vehicle "
                                    + v.getRegistrationNumber()
                                    + " (" + v.getMake() + " " + v.getModel()
                                    + ") is still under maintenance!");
                        }
                    }
                } catch (Exception e) {
                    // Silently ignore — monitor thread should not crash the app
                }
            }
        });
        monitor.setDaemon(true); // daemon thread exits when main thread exits
        monitor.start();
        FileLogger.log("Maintenance monitor thread started.");
    }

    // ==================== UTILITY METHODS ====================
    /**
     * Reads an integer from console, re-prompting on invalid input.
     */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a double from console, re-prompting on invalid input.
     */
    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
