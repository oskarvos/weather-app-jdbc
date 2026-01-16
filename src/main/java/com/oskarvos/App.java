package com.oskarvos;

import com.oskarvos.repository.WeatherRepository;
import com.oskarvos.server.SimpleHttpServer;
import com.oskarvos.util.DatabaseConnection;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println("=== Weather REST API ===");

            // Проверяем подключение к БД
            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("✅ Database connected successfully!");
            } catch (Exception e) {
                System.err.println("❌ Database connection failed: " + e.getMessage());
                return;
            }

            // Создаем репозиторий
            WeatherRepository repository = new WeatherRepository();

            // Запускаем HTTP сервер
            SimpleHttpServer server = new SimpleHttpServer(repository);
            server.start();

            System.out.println("\n📋 How to use:");
            System.out.println("1. Send POST request to: http://localhost:8080/weather");
            System.out.println("2. Content-Type: application/json");
            System.out.println("3. JSON body example: {\"city\":\"Moscow\",\"temperature\":22.5}");

        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}