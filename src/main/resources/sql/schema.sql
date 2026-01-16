CREATE TABLE IF NOT EXISTS weather_data
(
    weather_id  BIGSERIAL PRIMARY KEY,
    city        VARCHAR(20)  NOT NULL,
    temperature DECIMAL(5, 2) NOT NULL,
    record_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_city ON weather_data (city);
