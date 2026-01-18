package com.oskarvos;

import com.oskarvos.repository.WeatherRepository;
import com.oskarvos.server.SimpleHttpServer;
import com.oskarvos.util.DatabaseConnection;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println("Application \"weather-app-jdbc\" REST API");

            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connected!");
            } catch (Exception e) {
                System.err.println("Database connection failed: " + e.getMessage());
                return;
            }

            WeatherRepository repository = new WeatherRepository();

            SimpleHttpServer server = new SimpleHttpServer(repository);
            server.start();

        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // для запуска в другом проекте использовал как зависимость
    public static void start() {
        try {
            System.out.println("Application \"weather-app-jdbc\" REST API");

            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connected!");
            } catch (Exception e) {
                System.err.println("Database connection failed: " + e.getMessage());
                return;
            }

            WeatherRepository repository = new WeatherRepository();

            SimpleHttpServer server = new SimpleHttpServer(repository);
            server.start();

        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }

}