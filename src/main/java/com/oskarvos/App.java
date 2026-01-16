package com.oskarvos;

import com.oskarvos.controller.WeatherController;
import com.oskarvos.repository.WeatherRepository;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            System.out.println("Start weather application");

            WeatherRepository weatherRepository = new WeatherRepository();
            WeatherController weatherController = new WeatherController(weatherRepository);
            weatherController.start();

            System.out.println("Finished weather application");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
