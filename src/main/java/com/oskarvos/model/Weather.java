package com.oskarvos.model;

import java.time.LocalDateTime;

public class Weather {

    private Long id;
    private String city;
    private Double temperature;
    private LocalDateTime recordDateTime;

    public Weather() {
    }

    public Weather(String city, Double temperature) {
        this.city = city;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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
