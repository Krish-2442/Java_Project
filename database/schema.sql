-- =============================================
-- Fleet Management System - Database Schema
-- Database: fleet_management_db
-- Normalization: 3NF
-- =============================================

-- Create and use database
CREATE DATABASE IF NOT EXISTS fleet_management_db;
USE fleet_management_db;

-- =============================================
-- TABLE 1: Fleet
-- =============================================
CREATE TABLE Fleet (
    Fleet_ID INT AUTO_INCREMENT PRIMARY KEY,
    Fleet_Name VARCHAR(100) NOT NULL,
    Company_Name VARCHAR(100) NOT NULL,
    Contact_Phone VARCHAR(15) NOT NULL
);

-- =============================================
-- TABLE 2: Vehicle
-- =============================================
CREATE TABLE Vehicle (
    Vehicle_ID INT AUTO_INCREMENT PRIMARY KEY,
    Fleet_ID INT NOT NULL,
    Registration_Number VARCHAR(20) NOT NULL UNIQUE,
    Make VARCHAR(50) NOT NULL,
    Model VARCHAR(50) NOT NULL,
    Year INT NOT NULL,
    Fuel_Type VARCHAR(20) NOT NULL,
    Vehicle_Type VARCHAR(10) NOT NULL DEFAULT 'Car',
    Seating_Capacity INT DEFAULT 5,
    Status VARCHAR(20) NOT NULL DEFAULT 'Available',
    FOREIGN KEY (Fleet_ID) REFERENCES Fleet(Fleet_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- =============================================
-- TABLE 3: Driver
-- =============================================
CREATE TABLE Driver (
    Driver_ID INT AUTO_INCREMENT PRIMARY KEY,
    Fleet_ID INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    License_Number VARCHAR(30) NOT NULL UNIQUE,
    Phone VARCHAR(15) NOT NULL,
    Hire_Date DATE NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT 'Available',
    FOREIGN KEY (Fleet_ID) REFERENCES Fleet(Fleet_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- =============================================
-- TABLE 4: Trip
-- =============================================
CREATE TABLE Trip (
    Trip_ID INT AUTO_INCREMENT PRIMARY KEY,
    Vehicle_ID INT NOT NULL,
    Driver_ID INT NOT NULL,
    Departure_Date DATE NOT NULL,
    Arrival_Date DATE,
    Distance DOUBLE NOT NULL,
    Cost DOUBLE NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT 'Scheduled',
    FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Driver_ID) REFERENCES Driver(Driver_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- =============================================
-- TABLE 5: Maintenance
-- =============================================
CREATE TABLE Maintenance (
    Maintenance_ID INT AUTO_INCREMENT PRIMARY KEY,
    Vehicle_ID INT NOT NULL,
    Maintenance_Type VARCHAR(50) NOT NULL,
    Maintenance_Date DATE NOT NULL,
    Cost DOUBLE NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT 'Scheduled',
    FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- =============================================
-- SAMPLE DATA
-- =============================================

-- Fleet sample data (10 records)
INSERT INTO Fleet (Fleet_Name, Company_Name, Contact_Phone) VALUES
('North Fleet', 'TransCorp India', '9876543210'),
('South Fleet', 'TransCorp India', '9876543211'),
('East Fleet', 'QuickMove Logistics', '9123456780'),
('West Fleet', 'QuickMove Logistics', '9123456781'),
('Central Fleet', 'Metro Transport Co.', '9988776655'),
('Express Fleet', 'SpeedLine Services', '9871234560'),
('City Fleet', 'UrbanRide Pvt Ltd', '9009876543'),
('Highway Fleet', 'National Carriers', '9112233445'),
('Regional Fleet', 'StateLine Transport', '9334455667'),
('Premium Fleet', 'EliteDrive Services', '9556677889');

-- Vehicle sample data (15 records) — mix of Car and Bus types
INSERT INTO Vehicle (Fleet_ID, Registration_Number, Make, Model, Year, Fuel_Type, Vehicle_Type, Seating_Capacity, Status) VALUES
(1, 'GJ01AB1234', 'Tata', 'Nexon', 2022, 'Petrol', 'Car', 5, 'Available'),
(1, 'GJ01CD5678', 'Maruti', 'Swift', 2021, 'Diesel', 'Car', 5, 'In Service'),
(2, 'KA02EF9012', 'Hyundai', 'Creta', 2023, 'Petrol', 'Car', 5, 'Available'),
(2, 'KA02GH3456', 'Ashok Leyland', 'Viking', 2020, 'Diesel', 'Bus', 52, 'Available'),
(3, 'MH03IJ7890', 'Mahindra', 'Bolero', 2021, 'Diesel', 'Car', 7, 'Under Maintenance'),
(3, 'MH03KL1122', 'Tata', 'Starbus', 2022, 'Diesel', 'Bus', 40, 'Available'),
(4, 'RJ04MN3344', 'Toyota', 'Innova', 2023, 'Diesel', 'Car', 7, 'Available'),
(5, 'DL05OP5566', 'Honda', 'City', 2022, 'Petrol', 'Car', 5, 'In Service'),
(5, 'DL05QR7788', 'Eicher', 'Skyline', 2021, 'Diesel', 'Bus', 45, 'Available'),
(6, 'TN06ST9900', 'Kia', 'Seltos', 2023, 'Petrol', 'Car', 5, 'Available'),
(7, 'UP07UV1133', 'Tata', 'Harrier', 2022, 'Diesel', 'Car', 5, 'Available'),
(7, 'UP07WX2244', 'Volvo', '9400', 2023, 'Diesel', 'Bus', 48, 'In Service'),
(8, 'MP08YZ3355', 'Maruti', 'Ertiga', 2021, 'CNG', 'Car', 7, 'Available'),
(9, 'AP09AB4466', 'BharatBenz', '1017', 2020, 'Diesel', 'Bus', 36, 'Under Maintenance'),
(10, 'HR10CD5577', 'Mercedes', 'V-Class', 2023, 'Diesel', 'Car', 7, 'Available');

-- Driver sample data (12 records)
INSERT INTO Driver (Fleet_ID, Name, License_Number, Phone, Hire_Date, Status) VALUES
(1, 'Rajesh Kumar', 'DL-0420210012345', '9001122334', '2021-03-15', 'Available'),
(1, 'Suresh Patel', 'GJ-0120200098765', '9001122335', '2020-06-10', 'On Trip'),
(2, 'Anil Sharma', 'KA-0220220045678', '9112233446', '2022-01-20', 'Available'),
(3, 'Mohammed Ali', 'MH-0320210067890', '9223344557', '2021-07-05', 'Available'),
(3, 'Vikram Singh', 'MH-0320230011223', '9223344558', '2023-02-14', 'On Leave'),
(4, 'Deepak Verma', 'RJ-0420220033445', '9334455668', '2022-09-01', 'Available'),
(5, 'Pradeep Gupta', 'DL-0520210055667', '9445566779', '2021-11-25', 'On Trip'),
(6, 'Karthik Rajan', 'TN-0620230077889', '9556677880', '2023-04-10', 'Available'),
(7, 'Amit Yadav', 'UP-0720220099001', '9667788991', '2022-05-18', 'Available'),
(8, 'Sanjay Mishra', 'MP-0820210011112', '9778899002', '2021-08-30', 'Available'),
(9, 'Ravi Reddy', 'AP-0920230033334', '9889900113', '2023-06-22', 'On Trip'),
(10, 'Manish Joshi', 'HR-1020220055556', '9990011224', '2022-12-01', 'Available');

-- Trip sample data (12 records)
INSERT INTO Trip (Vehicle_ID, Driver_ID, Departure_Date, Arrival_Date, Distance, Cost, Status) VALUES
(1, 1, '2024-01-10', '2024-01-10', 120.5, 1500.00, 'Completed'),
(2, 2, '2024-01-12', '2024-01-13', 450.0, 5500.00, 'Completed'),
(3, 3, '2024-01-15', '2024-01-15', 80.0, 1000.00, 'Completed'),
(4, 4, '2024-01-18', NULL, 600.0, 8000.00, 'In Progress'),
(7, 6, '2024-01-20', '2024-01-21', 350.0, 4200.00, 'Completed'),
(8, 7, '2024-02-01', NULL, 200.0, 2500.00, 'In Progress'),
(10, 8, '2024-02-05', '2024-02-05', 95.0, 1200.00, 'Completed'),
(11, 9, '2024-02-10', '2024-02-11', 520.0, 6500.00, 'Completed'),
(13, 10, '2024-02-15', NULL, 300.0, 3800.00, 'In Progress'),
(15, 12, '2024-02-20', '2024-02-21', 280.0, 3500.00, 'Completed'),
(1, 1, '2024-03-01', '2024-03-01', 150.0, 1800.00, 'Completed'),
(3, 3, '2024-03-05', NULL, 400.0, 5000.00, 'Scheduled');

-- Maintenance sample data (10 records)
INSERT INTO Maintenance (Vehicle_ID, Maintenance_Type, Maintenance_Date, Cost, Status) VALUES
(1, 'Oil Change', '2024-01-05', 2500.00, 'Completed'),
(2, 'Tire Replacement', '2024-01-08', 8000.00, 'Completed'),
(5, 'Engine Repair', '2024-01-20', 15000.00, 'In Progress'),
(4, 'Brake Inspection', '2024-02-01', 3000.00, 'Completed'),
(6, 'Battery Replacement', '2024-02-10', 5000.00, 'Completed'),
(8, 'Oil Change', '2024-02-15', 2500.00, 'Scheduled'),
(9, 'AC Service', '2024-02-20', 4000.00, 'Completed'),
(11, 'Tire Replacement', '2024-03-01', 8500.00, 'Scheduled'),
(14, 'Engine Overhaul', '2024-03-05', 25000.00, 'In Progress'),
(15, 'General Service', '2024-03-10', 6000.00, 'Scheduled');


-- #############################################################
-- ADVANCED SQL FEATURES (to impress teacher)
-- #############################################################

-- =============================================
-- VIEWS — Virtual tables for quick reporting
-- =============================================

-- VIEW 1: Fleet Summary — shows each fleet with vehicle count and driver count
CREATE OR REPLACE VIEW Fleet_Summary AS
SELECT
    f.Fleet_ID,
    f.Fleet_Name,
    f.Company_Name,
    (SELECT COUNT(*) FROM Vehicle v WHERE v.Fleet_ID = f.Fleet_ID) AS Total_Vehicles,
    (SELECT COUNT(*) FROM Driver d WHERE d.Fleet_ID = f.Fleet_ID) AS Total_Drivers
FROM Fleet f;

-- VIEW 2: Trip Details — joins Trip with Vehicle and Driver names
CREATE OR REPLACE VIEW Trip_Details AS
SELECT
    t.Trip_ID,
    v.Registration_Number,
    CONCAT(v.Make, ' ', v.Model) AS Vehicle_Name,
    d.Name AS Driver_Name,
    t.Departure_Date,
    t.Arrival_Date,
    t.Distance,
    t.Cost,
    t.Status
FROM Trip t
JOIN Vehicle v ON t.Vehicle_ID = v.Vehicle_ID
JOIN Driver d ON t.Driver_ID = d.Driver_ID;

-- VIEW 3: Vehicle Maintenance History — vehicle info + maintenance records
CREATE OR REPLACE VIEW Vehicle_Maintenance_History AS
SELECT
    v.Registration_Number,
    CONCAT(v.Make, ' ', v.Model) AS Vehicle_Name,
    m.Maintenance_Type,
    m.Maintenance_Date,
    m.Cost,
    m.Status
FROM Maintenance m
JOIN Vehicle v ON m.Vehicle_ID = v.Vehicle_ID
ORDER BY m.Maintenance_Date DESC;

-- VIEW 4: Available Resources — shows all available vehicles and drivers
CREATE OR REPLACE VIEW Available_Resources AS
SELECT
    'Vehicle' AS Resource_Type,
    v.Vehicle_ID AS Resource_ID,
    CONCAT(v.Make, ' ', v.Model, ' (', v.Registration_Number, ')') AS Resource_Name,
    f.Fleet_Name
FROM Vehicle v
JOIN Fleet f ON v.Fleet_ID = f.Fleet_ID
WHERE v.Status = 'Available'
UNION ALL
SELECT
    'Driver' AS Resource_Type,
    d.Driver_ID AS Resource_ID,
    CONCAT(d.Name, ' (', d.License_Number, ')') AS Resource_Name,
    f.Fleet_Name
FROM Driver d
JOIN Fleet f ON d.Fleet_ID = f.Fleet_ID
WHERE d.Status = 'Available';


-- =============================================
-- STORED PROCEDURES — Reusable business logic
-- =============================================

-- PROCEDURE 1: Add a new fleet (INSERT with validation)
DELIMITER //
CREATE PROCEDURE sp_AddFleet(
    IN p_Fleet_Name VARCHAR(100),
    IN p_Company_Name VARCHAR(100),
    IN p_Contact_Phone VARCHAR(15)
)
BEGIN
    IF p_Fleet_Name IS NULL OR p_Fleet_Name = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fleet name cannot be empty';
    END IF;
    INSERT INTO Fleet (Fleet_Name, Company_Name, Contact_Phone)
    VALUES (p_Fleet_Name, p_Company_Name, p_Contact_Phone);
    SELECT LAST_INSERT_ID() AS New_Fleet_ID;
END //
DELIMITER ;

-- PROCEDURE 2: Register a vehicle (INSERT)
DELIMITER //
CREATE PROCEDURE sp_RegisterVehicle(
    IN p_Fleet_ID INT,
    IN p_Reg_Number VARCHAR(20),
    IN p_Make VARCHAR(50),
    IN p_Model VARCHAR(50),
    IN p_Year INT,
    IN p_Fuel_Type VARCHAR(20),
    IN p_Vehicle_Type VARCHAR(10),
    IN p_Seating_Capacity INT
)
BEGIN
    -- Check if fleet exists
    IF NOT EXISTS (SELECT 1 FROM Fleet WHERE Fleet_ID = p_Fleet_ID) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fleet ID does not exist';
    END IF;
    -- Check duplicate registration number
    IF EXISTS (SELECT 1 FROM Vehicle WHERE Registration_Number = p_Reg_Number) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Registration number already exists';
    END IF;
    INSERT INTO Vehicle (Fleet_ID, Registration_Number, Make, Model, Year,
                         Fuel_Type, Vehicle_Type, Seating_Capacity, Status)
    VALUES (p_Fleet_ID, p_Reg_Number, p_Make, p_Model, p_Year,
            p_Fuel_Type, p_Vehicle_Type, p_Seating_Capacity, 'Available');
    SELECT LAST_INSERT_ID() AS New_Vehicle_ID;
END //
DELIMITER ;

-- PROCEDURE 3: Delete a vehicle by ID (DELETE)
DELIMITER //
CREATE PROCEDURE sp_DeleteVehicle(
    IN p_Vehicle_ID INT
)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Vehicle WHERE Vehicle_ID = p_Vehicle_ID) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Vehicle not found';
    END IF;
    DELETE FROM Vehicle WHERE Vehicle_ID = p_Vehicle_ID;
    SELECT CONCAT('Vehicle ', p_Vehicle_ID, ' deleted successfully') AS Result;
END //
DELIMITER ;

-- PROCEDURE 4: Update vehicle status (UPDATE)
DELIMITER //
CREATE PROCEDURE sp_UpdateVehicleStatus(
    IN p_Vehicle_ID INT,
    IN p_New_Status VARCHAR(20)
)
BEGIN
    IF p_New_Status NOT IN ('Available', 'In Service', 'Under Maintenance') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid status value';
    END IF;
    UPDATE Vehicle SET Status = p_New_Status WHERE Vehicle_ID = p_Vehicle_ID;
    SELECT CONCAT('Vehicle ', p_Vehicle_ID, ' status changed to ', p_New_Status) AS Result;
END //
DELIMITER ;

-- PROCEDURE 5: Create a trip (INSERT with cost calculation)
DELIMITER //
CREATE PROCEDURE sp_CreateTrip(
    IN p_Vehicle_ID INT,
    IN p_Driver_ID INT,
    IN p_Departure_Date DATE,
    IN p_Distance DOUBLE
)
BEGIN
    DECLARE v_Cost DOUBLE;
    -- Check vehicle is available
    IF NOT EXISTS (SELECT 1 FROM Vehicle WHERE Vehicle_ID = p_Vehicle_ID AND Status = 'Available') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Vehicle is not available for trip';
    END IF;
    -- Check driver is available
    IF NOT EXISTS (SELECT 1 FROM Driver WHERE Driver_ID = p_Driver_ID AND Status = 'Available') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Driver is not available for trip';
    END IF;
    -- Auto-calculate cost at Rs.12 per km
    SET v_Cost = p_Distance * 12.0;
    INSERT INTO Trip (Vehicle_ID, Driver_ID, Departure_Date, Distance, Cost, Status)
    VALUES (p_Vehicle_ID, p_Driver_ID, p_Departure_Date, p_Distance, v_Cost, 'Scheduled');
    -- Update vehicle and driver status
    UPDATE Vehicle SET Status = 'In Service' WHERE Vehicle_ID = p_Vehicle_ID;
    UPDATE Driver SET Status = 'On Trip' WHERE Driver_ID = p_Driver_ID;
    SELECT LAST_INSERT_ID() AS New_Trip_ID, v_Cost AS Estimated_Cost;
END //
DELIMITER ;

-- PROCEDURE 6: Complete a trip (UPDATE with arrival date)
DELIMITER //
CREATE PROCEDURE sp_CompleteTrip(
    IN p_Trip_ID INT
)
BEGIN
    DECLARE v_Vehicle_ID INT;
    DECLARE v_Driver_ID INT;
    -- Fetch vehicle and driver from trip
    SELECT Vehicle_ID, Driver_ID INTO v_Vehicle_ID, v_Driver_ID
    FROM Trip WHERE Trip_ID = p_Trip_ID;
    IF v_Vehicle_ID IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Trip not found';
    END IF;
    -- Mark trip as completed with today's date
    UPDATE Trip SET Status = 'Completed', Arrival_Date = CURDATE()
    WHERE Trip_ID = p_Trip_ID;
    -- Free up vehicle and driver
    UPDATE Vehicle SET Status = 'Available' WHERE Vehicle_ID = v_Vehicle_ID;
    UPDATE Driver SET Status = 'Available' WHERE Driver_ID = v_Driver_ID;
    SELECT CONCAT('Trip ', p_Trip_ID, ' completed successfully') AS Result;
END //
DELIMITER ;

-- PROCEDURE 7: Get fleet report (SELECT with aggregation)
DELIMITER //
CREATE PROCEDURE sp_FleetReport(
    IN p_Fleet_ID INT
)
BEGIN
    SELECT f.Fleet_Name, f.Company_Name,
           COUNT(DISTINCT v.Vehicle_ID) AS Vehicles,
           COUNT(DISTINCT d.Driver_ID) AS Drivers,
           COALESCE(SUM(t.Cost), 0) AS Total_Trip_Revenue,
           COALESCE(SUM(t.Distance), 0) AS Total_Distance_Covered
    FROM Fleet f
    LEFT JOIN Vehicle v ON f.Fleet_ID = v.Fleet_ID
    LEFT JOIN Driver d ON f.Fleet_ID = d.Fleet_ID
    LEFT JOIN Trip t ON v.Vehicle_ID = t.Vehicle_ID
    WHERE f.Fleet_ID = p_Fleet_ID
    GROUP BY f.Fleet_ID, f.Fleet_Name, f.Company_Name;
END //
DELIMITER ;


-- =============================================
-- TRIGGERS — Automatic actions on data changes
-- =============================================

-- TRIGGER 1: When a vehicle is deleted, log the event
-- (creates an audit log table first)
CREATE TABLE IF NOT EXISTS Audit_Log (
    Log_ID INT AUTO_INCREMENT PRIMARY KEY,
    Action_Type VARCHAR(50) NOT NULL,
    Table_Name VARCHAR(50) NOT NULL,
    Record_ID INT,
    Description VARCHAR(255),
    Action_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER //
CREATE TRIGGER trg_AfterVehicleDelete
AFTER DELETE ON Vehicle
FOR EACH ROW
BEGIN
    INSERT INTO Audit_Log (Action_Type, Table_Name, Record_ID, Description)
    VALUES ('DELETE', 'Vehicle', OLD.Vehicle_ID,
            CONCAT('Deleted vehicle: ', OLD.Registration_Number, ' (', OLD.Make, ' ', OLD.Model, ')'));
END //
DELIMITER ;

-- TRIGGER 2: When a new driver is inserted, log it
DELIMITER //
CREATE TRIGGER trg_AfterDriverInsert
AFTER INSERT ON Driver
FOR EACH ROW
BEGIN
    INSERT INTO Audit_Log (Action_Type, Table_Name, Record_ID, Description)
    VALUES ('INSERT', 'Driver', NEW.Driver_ID,
            CONCAT('New driver hired: ', NEW.Name, ' (License: ', NEW.License_Number, ')'));
END //
DELIMITER ;

-- TRIGGER 3: When maintenance status changes to 'Completed', mark vehicle as 'Available'
DELIMITER //
CREATE TRIGGER trg_AfterMaintenanceUpdate
AFTER UPDATE ON Maintenance
FOR EACH ROW
BEGIN
    IF NEW.Status = 'Completed' AND OLD.Status != 'Completed' THEN
        UPDATE Vehicle SET Status = 'Available' WHERE Vehicle_ID = NEW.Vehicle_ID;
    END IF;
END //
DELIMITER ;


-- =============================================
-- USEFUL QUERIES — For reporting and analysis
-- =============================================

-- QUERY 1: Total revenue per fleet (GROUP BY + JOIN)
SELECT f.Fleet_Name, f.Company_Name,
       COUNT(t.Trip_ID) AS Total_Trips,
       COALESCE(SUM(t.Cost), 0) AS Total_Revenue,
       COALESCE(SUM(t.Distance), 0) AS Total_Distance
FROM Fleet f
LEFT JOIN Vehicle v ON f.Fleet_ID = v.Fleet_ID
LEFT JOIN Trip t ON v.Vehicle_ID = t.Vehicle_ID
GROUP BY f.Fleet_ID, f.Fleet_Name, f.Company_Name
ORDER BY Total_Revenue DESC;

-- QUERY 2: Vehicle utilization — trips per vehicle (Subquery)
SELECT v.Registration_Number,
       CONCAT(v.Make, ' ', v.Model) AS Vehicle,
       v.Status,
       (SELECT COUNT(*) FROM Trip t WHERE t.Vehicle_ID = v.Vehicle_ID) AS Trip_Count,
       (SELECT COALESCE(SUM(t.Distance), 0) FROM Trip t WHERE t.Vehicle_ID = v.Vehicle_ID) AS Total_KM
FROM Vehicle v
ORDER BY Trip_Count DESC;

-- QUERY 3: Drivers who have never been on a trip (NOT EXISTS)
SELECT d.Driver_ID, d.Name, d.License_Number, d.Status
FROM Driver d
WHERE NOT EXISTS (
    SELECT 1 FROM Trip t WHERE t.Driver_ID = d.Driver_ID
);

-- QUERY 4: Highest maintenance cost vehicles (HAVING + GROUP BY)
SELECT v.Registration_Number,
       CONCAT(v.Make, ' ', v.Model) AS Vehicle,
       COUNT(m.Maintenance_ID) AS Maintenance_Count,
       SUM(m.Cost) AS Total_Maintenance_Cost
FROM Vehicle v
JOIN Maintenance m ON v.Vehicle_ID = m.Vehicle_ID
GROUP BY v.Vehicle_ID, v.Registration_Number, v.Make, v.Model
HAVING SUM(m.Cost) > 5000
ORDER BY Total_Maintenance_Cost DESC;

-- QUERY 5: Monthly trip summary (DATE functions)
SELECT
    DATE_FORMAT(t.Departure_Date, '%Y-%m') AS Month,
    COUNT(*) AS Trips,
    SUM(t.Distance) AS Total_Distance,
    SUM(t.Cost) AS Total_Cost,
    AVG(t.Cost) AS Avg_Cost_Per_Trip
FROM Trip t
GROUP BY DATE_FORMAT(t.Departure_Date, '%Y-%m')
ORDER BY Month;

-- QUERY 6: Vehicles that need maintenance soon (last service > 60 days ago)
SELECT v.Registration_Number,
       CONCAT(v.Make, ' ', v.Model) AS Vehicle,
       MAX(m.Maintenance_Date) AS Last_Service_Date,
       DATEDIFF(CURDATE(), MAX(m.Maintenance_Date)) AS Days_Since_Service
FROM Vehicle v
LEFT JOIN Maintenance m ON v.Vehicle_ID = m.Vehicle_ID AND m.Status = 'Completed'
GROUP BY v.Vehicle_ID, v.Registration_Number, v.Make, v.Model
HAVING Days_Since_Service > 60 OR Last_Service_Date IS NULL
ORDER BY Days_Since_Service DESC;


-- =============================================
-- DEMONSTRATION QUERIES (INSERT, UPDATE, DELETE)
-- Run these to show dynamic data operations
-- =============================================

-- DEMO: INSERT a new fleet using stored procedure
CALL sp_AddFleet('Demo Fleet', 'Demo Transport Co.', '9999988888');

-- DEMO: Register a new vehicle using stored procedure
CALL sp_RegisterVehicle(11, 'GJ05XY9999', 'Tata', 'Punch', 2024, 'Petrol', 'Car', 5);

-- DEMO: Create a trip (auto-calculates cost, updates vehicle/driver status)
-- Note: Use an available vehicle and driver; IDs may vary after above inserts
CALL sp_CreateTrip(16, 1, '2024-04-01', 250.0);

-- DEMO: Complete the trip (marks trip done, frees vehicle and driver)
CALL sp_CompleteTrip(13);

-- DEMO: Update vehicle status
CALL sp_UpdateVehicleStatus(3, 'Under Maintenance');

-- DEMO: Delete a vehicle and check audit log
CALL sp_DeleteVehicle(16);
SELECT * FROM Audit_Log;

-- DEMO: View reports using views
SELECT * FROM Fleet_Summary;
SELECT * FROM Trip_Details;
SELECT * FROM Vehicle_Maintenance_History;
SELECT * FROM Available_Resources;

-- DEMO: Get fleet report
CALL sp_FleetReport(1);
