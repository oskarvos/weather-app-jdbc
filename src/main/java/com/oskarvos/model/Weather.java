package com.oskarvos.model;

import java.time.LocalDateTime;

public class Weather {

    private Long id;
    private String city;
    private Double temperature;
    private LocalDateTime recordDateTime;

    public Weather() {
    }

    public Weather(String city, Double temperature, LocalDateTime recordDateTime) {
        this.city = city;
        this.temperature = temperature;
        this.recordDateTime = recordDateTime;
    }

    public String getCity() {
        return city;
    }

    public Double getTemperature() {
        return temperature;
    }

    public LocalDateTime getRecordDateTime() {
        return recordDateTime;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setRecordDateTime(LocalDateTime recordDateTime) {
        this.recordDateTime = recordDateTime;
    }

}
