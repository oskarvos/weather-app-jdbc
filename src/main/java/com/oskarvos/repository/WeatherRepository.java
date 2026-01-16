package com.oskarvos.repository;

import com.oskarvos.model.Weather;
import com.oskarvos.util.DatabaseConnection;

import java.sql.*;

public class WeatherRepository {

    public Weather save(Weather weather) {

        String sql = "INSERT INTO weather (city, temperature) VALUES (?,?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, weather.getCity());
            pstmt.setDouble(2, weather.getTemperature());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();

            try (ResultSet resultSet = pstmt.getResultSet()) {
                if (generatedKeys.next()) {
                    weather.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("created weather failed, no ID.");
                }
            }

            return weather;

        } catch (Exception e) {
            throw new RuntimeException("Database error saving weather", e);
        }
    }

}
