package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Rezume Spring Boot application.
 * <p>
 * This class contains the main method which starts the application.
 */
@SpringBootApplication
public class RezumeApplication {

    /**
     * Main method to launch the Rezume application.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(RezumeApplication.class, args);
    }
}