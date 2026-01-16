package com.oskarvos.controller;

import com.oskarvos.repository.WeatherRepository;

public class WeatherController {

    private WeatherRepository weatherRepository;

    public WeatherController(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void start() {

    }

}
