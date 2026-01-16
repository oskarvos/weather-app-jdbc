package com.oskarvos.server;

import com.oskarvos.model.Weather;
import com.oskarvos.repository.WeatherRepository;
import com.oskarvos.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WeatherHandler implements HttpHandler {

    private final WeatherRepository weatherRepository;
    private final JsonUtil jsonUtil;

    public WeatherHandler(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
        this.jsonUtil = new JsonUtil();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if ("POST".equals(method)) {
            handlePost(exchange);
        } else {
            sendResponse(exchange, 405, "Method failed");
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        try {
            // Читаем тело запроса
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // Парсим JSON
            Weather weather = jsonUtil.fromJson(body, Weather.class);

            if (weather.getCity() == null || weather.getTemperature() == null) {
                sendResponse(exchange, 400, "Invalid JSON format");
                return;
            }

            // Сохраняем в БД
            Weather savedWeather = weatherRepository.save(weather);

            // Формируем JSON ответ
            String response = jsonUtil.toJson(savedWeather);

            sendJsonResponse(exchange, 201, response);

        } catch (Exception e) {
            sendResponse(exchange, 500, "Server Error: " + e.getMessage());
        }
    }

    private void sendResponse(HttpExchange exchange, int code, String response)
            throws IOException {
        exchange.sendResponseHeaders(code, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void sendJsonResponse(HttpExchange exchange, int code, String json)
            throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        sendResponse(exchange, code, json);
    }

}
