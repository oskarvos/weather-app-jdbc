package com.oskarvos.server;

import com.oskarvos.model.Weather;
import com.oskarvos.repository.WeatherRepository;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WeatherHandler implements HttpHandler {

    private final WeatherRepository weatherRepository;

    public WeatherHandler(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
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

            // Парсим JSON вручную (простой вариант)
            // Формат: {"city":"Moscow","temperature":22.5}
            Weather weather = parseWeatherData(body);

            if (weather.getCity() == null || weather.getTemperature() == null) {
                sendResponse(exchange, 400, "Invalid JSON format");
                return;
            }

            // Сохраняем в БД
            Weather savedWeather = weatherRepository.save(weather);

            // Формируем JSON ответ
            String response = String.format(
                    "{\"id\":%d,\"city\":\"%s\",\"temperature\":%.1f,\"message\":\"Weather saved successfully\"}",
                    savedWeather.getId(), savedWeather.getCity(), savedWeather.getTemperature()
            );

            sendJsonResponse(exchange, 201, response);

        } catch (Exception e) {
            sendResponse(exchange, 500, "Server Error: " + e.getMessage());
        }
    }

    // Простой парсинг JSON без библиотек
    private Weather parseWeatherData(String json) {
        try {
            // Удаляем пробелы и фигурные скобки
            json = json.trim().replace("{", "").replace("}", "");

            String city = null;
            Double temperature = null;

            // Разбиваем по запятым
            String[] parts = json.split(",");
            for (String part : parts) {
                part = part.trim();
                if (part.startsWith("\"city\":")) {
                    city = part.substring(8, part.length() - 1); // Убираем "city":" и "
                } else if (part.startsWith("\"temperature\":")) {
                    temperature = Double.parseDouble(part.substring(14));
                }
            }

            if (city != null && temperature != null) {
                return new Weather(city, temperature);
            }
        } catch (Exception e) {
            // Если парсинг не удался
        }
        return new Weather();
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
