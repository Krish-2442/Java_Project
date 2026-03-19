package com.fleetmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application entry point.
 * Starts the embedded Tomcat server and initializes all REST controllers.
 */
@SpringBootApplication
public class FleetManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetManagementApplication.class, args);
        System.out.println("=================================================");
        System.out.println("  Fleet Management System API is running!");
        System.out.println("  API:       http://localhost:8080/api");
        System.out.println("  Dashboard: Open website/index.html in browser");
        System.out.println("=================================================");
    }
}
