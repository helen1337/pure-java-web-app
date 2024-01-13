package service;

import models.WeatherForecast;
import parsers.WeatherJSONParser;

import java.io.IOException;

/**
 * Service class for retrieving weather forecasts
 */
public class WeatherForecastService {

    /**
     * Searches for weather information for a specific city
     *
     * @param city The name of the city for which to retrieve
     *             weather information
     * @return WeatherForecast object representing the parsed weather information
     * @throws IOException If an I/O error occurs while making the weather request
     */
    public static WeatherForecast searchWeatherCity(String city) throws IOException {
        return WeatherJSONParser.parsJsonByCity(city);
    }
}
