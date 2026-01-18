package com.oskarvos.server;

import com.oskarvos.repository.WeatherRepository;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    private final WeatherRepository weatherRepository;

    public SimpleHttpServer(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/weather", new WeatherHandler(weatherRepository));
        server.setExecutor(null);

        System.out.println("Server started on http://localhost:8080");
        server.start();
    }

}
