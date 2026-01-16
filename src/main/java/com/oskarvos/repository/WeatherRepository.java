package com.oskarvos.repository;

import com.oskarvos.model.Weather;
import com.oskarvos.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherRepository {

    public Weather save(Weather weather) {

        String sql = "INSERT INTO weather (city, temperature, record_date) VALUES (?,?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, weather.getCity());
            pstmt.setDouble(2, weather.getTemperature());

            ResultSet generatedKeys = pstmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                weather.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("created weather failed, no ID.");
            }

            return weather;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
