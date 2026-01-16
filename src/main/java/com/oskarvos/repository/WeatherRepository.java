package com.oskarvos.repository;

import com.oskarvos.model.Weather;
import com.oskarvos.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class WeatherRepository {

    public Weather save(Weather weather) {
        String sql = "INSERT INTO weather_data (city, temperature) VALUES (?,?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, weather.getCity());
            pstmt.setDouble(2, weather.getTemperature());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    weather.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Created weather failed, no ID.");
                }
            }

            weather.setRecordDateTime(LocalDateTime.now());

            return weather;

        } catch (Exception e) {
            throw new RuntimeException("Database error saving weather", e);
        }
    }

}
